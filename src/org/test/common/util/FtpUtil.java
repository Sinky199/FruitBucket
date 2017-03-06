package org.test.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * <pre>
 * Class Name: com.avit.util.ftp.FtpUtil
 * 向FTP服务器上传文件的工具类。
 * </pre>
 * @author Administrator
 * @version 1.0, 2011-8-4
 * @since
 */
public class FtpUtil {
	private static Log log= LogFactory.getLog(FtpUtil.class);
	
	private static final String ENCODING = "UTF-8";

    private static String encoding = System.getProperty("file.encoding");

	public FTPClient ftpClient = new FTPClient();
	
	private String ftpURL;
	private int ftpport;

	private String username;
	private String pwd;

	/**
	 * 构造函数
	 * @param ftpURL    FTP服务器地址
	 * @param ftpport   FTP服务器端口
	 * @param username  FTP用户名
	 * @param pwd       FTP用户密码
	 */
	public FtpUtil(String ftpURL, int ftpport, String username, String pwd) {
		this.ftpURL = ftpURL;
		this.ftpport = ftpport;
		
		this.username = username;
		this.pwd = pwd;
		// 设置PassiveMode传输
		ftpClient.enterLocalPassiveMode();
		
		ftpClient.setControlEncoding(ENCODING);
		// 设置将上传过程中使用到的FTP命令输出到控制台
		this.ftpClient.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out)));
	}

	/**
	 * 连接到FTP服务器
	 * 
	 * @return  链接是否成功
	 * @throws java.io.IOException
	 */
	public boolean connect() throws IOException {
		ftpClient.connect(ftpURL, ftpport);
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login(username, pwd)) {
				return true;
			}
		}
		disconnect();
		return false;
	}

	/**
	 * 断开与远程服务器的连接
	 *
	 * @throws java.io.IOException
	 */
	public void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.logout();
			ftpClient.disconnect();
		}
	}

	/**
	 * <pre>
	 * 把本地文件上传到FTP服务器,
	 * 把本地文件内容复制到远程文件。
	 * </pre>
	 * @param localFile    本地文件
	 * @param remoteFile   远程文件
	 * @return
	 * @throws java.io.IOException
	 */
	public UploadStatus uploadFile(String localFile, String remoteFile) throws IOException {
		//把上传目录中的“\"替换为”/“
		localFile = localFile.replace("\\", "/");
		remoteFile = remoteFile.replace("\\", "/");

		UploadStatus result;
		// 对远程目录的处理
		String remoteFileName = remoteFile;
		if (remoteFile.contains("/")) {
			remoteFileName = remoteFile.substring(remoteFile.lastIndexOf("/") + 1);
			// 创建服务器远程目录结构，创建失败直接返回
			if (CreateDirecroty(remoteFile) == UploadStatus.Create_Directory_Fail) {
				return UploadStatus.Create_Directory_Fail;
			}
		}

		File lFile = new File(localFile);
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//开始上传文件
		result = uploadFile2FTP(lFile, remoteFileName);
		return result;
	}
	/**
         * <pre>
         * 把本地文件上传到FTP服务器,
         * 把本地文件内容复制到远程文件。
         * </pre>
         * @param localFile    本地文件
         * @param remoteFile   远程文件
         * @return
         * @throws java.io.IOException
         */
        public UploadStatus uploadFile2(File localFile, String remoteFile) throws IOException {
                //把上传目录中的“\"替换为”/“
                remoteFile = remoteFile.replace("\\", "/");

                UploadStatus result;
                // 对远程目录的处理
                String remoteFileName = remoteFile;
                if (remoteFile.contains("/")) {
                        remoteFileName = remoteFile.substring(remoteFile.lastIndexOf("/") + 1);
                        // 创建服务器远程目录结构，创建失败直接返回
                        if (CreateDirecroty(remoteFile) == UploadStatus.Create_Directory_Fail) {
                                return UploadStatus.Create_Directory_Fail;
                        }
                }

                // 设置以二进制流的方式传输
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                //开始上传文件
                result = uploadFile2FTP(localFile, remoteFileName);
                return result;
        }

	/**
	 * <pre>
	 * 上传文件夹,把指定文件夹下的文件和子目录都上传的服务器。
	 * </pre>
	 * @param localDir        本地绝对路径，路径名最后不带“/”
	 * @param remoteDir
	 * @throws java.io.IOException
	 */
	public void uploadDirectory(String localDir, String remoteDir) throws IOException {
		if (localDir == null || remoteDir == null || "".equals(localDir)) {
			return;
		}
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BLOCK_TRANSFER_MODE);
		//改变FTP服务器的路径为remoteDir
		changeRemoteDir(remoteDir);
		//去掉目录末尾的”/“
		int i = localDir.lastIndexOf("/")+1;
		if (i == localDir.length()) {
			localDir = localDir.substring(0,localDir.lastIndexOf("/"));
		}
		//获取上传文件路径的根目录
		String root = localDir.substring(localDir.lastIndexOf("/") + 1);
		//上传目录
		uploadDir(localDir, remoteDir, root);
	}

	/**
	 * 递归创建远程服务器目录
	 *
	 * @param remote  远程服务器文件绝对路径
	 * @return 目录创建是否成功
	 * @throws java.io.IOException
	 */
	private UploadStatus CreateDirecroty(String remote) throws IOException {
		UploadStatus status = UploadStatus.Create_Directory_Success;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equals("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory
						.getBytes("GBK"), "iso-8859-1"))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end)
						.getBytes("GBK"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						log.info("创建目录失败");
						return UploadStatus.Create_Directory_Fail;
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);

				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return status;
	}

	/**
	 * 上传文件夹，把本地文件夹复制到FTP服务器
	 *
	 * @param localDir      本地绝对路径，路径名最后不带“/”
	 * @param remoteDir     FTP服务器上的工作目录
	 * @param root          上传文件目录的根路径
	 * @return
	 * @throws java.io.IOException
	 */
	private UploadStatus uploadDir(String localDir, String remoteDir, String root) throws IOException {
		//初始化状态
		UploadStatus result = UploadStatus.Upload_New_File_Success;
		//把上传目录中的“\"替换为”/“
		localDir = localDir.replace("\\", "/");

		File lFile = new File(localDir);

		//如果文件时目录
		if (lFile.isDirectory()) {

			String baseDir = localDir.substring(localDir.lastIndexOf("/") + 1);

			//改变FTP服务器的路径
			changeRemoteDir(baseDir);
			//列出本地文件列表
			File[] subFiles = lFile.listFiles();
			for(File f: subFiles) {
				this.uploadDir(f.getPath(), remoteDir, root);
			}
			//回答上一级目录
			ftpClient.changeToParentDirectory();
		} else {
			//改变FTP服务器的路径
			changeRemoteDir(remoteDir);

			int index = localDir.indexOf(root);
			String baseDir = localDir.substring(index, localDir.lastIndexOf("/"));

			//改变FTP服务器的路径
			changeRemoteDir(baseDir);

			String remoteFileName = lFile.getName();
			//开始上传文件
			result = uploadFile2FTP(lFile, remoteFileName);
		}
		return result;
	}

    /**
         * Description: 从FTP服务器下载文件
         *
         * @Version1.0
         * @param url
         *            FTP服务器hostname
         * @param port
         *            FTP服务器端口
         * @param username
         *            FTP登录账号
         * @param password
         *            FTP登录密码
         * @param remotePath
         *            FTP服务器上的相对路径
         * @param fileName
         *            要下载的文件名
         * @param localPath
         *            下载后保存到本地的路径
         * @return
         */
        public boolean downloadFile(String remotePath, String fileName, String localPath) {
            boolean result = false;
            OutputStream is = null;
            try{
                // 转移工作目录至指定目录下
                ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(encoding),"iso-8859-1"));
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                File localFile = new File(localPath + "/" + fileName);
                is = new FileOutputStream(localFile);
                ftpClient.retrieveFile(fileName, is);
            }catch (IOException e){
                log.error(e.getMessage(), e);
            }finally {
                try {
                    is.close();
                    this.disconnect();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            return result;
        }


	private UploadStatus uploadFile2FTP(File lFile, String remoteFileName)
			throws IOException {
		UploadStatus result;
		// 检查远程是否存在文件
		FTPFile[] files = ftpClient.listFiles(remoteFileName);
		if (files.length == 1) {
			deleteFile(remoteFileName);
			result = continueTransFile(remoteFileName, lFile, 0);
			/*
			long remoteSize = files[0].getSize();

			long localSize = lFile.length();
			if (remoteSize == localSize) {
				return UploadStatus.File_Exits;
			} else if (remoteSize > localSize) {
				return UploadStatus.Remote_Bigger_Local;
			}

			// 尝试移动文件内读取指针,进行文件续传
			result = continueTransFile(remoteFileName, lFile, remoteSize);

			// 如果断点续传没有成功，则删除服务器上文件，重新上传
			if (result == UploadStatus.Upload_From_Break_Failed) {
				if (!ftpClient.deleteFile(remoteFileName)) {
					return UploadStatus.Delete_Remote_Faild;
				}
				result = continueTransFile(remoteFileName, lFile, 0);
			}
			*/
		} else {
			result = continueTransFile(remoteFileName, lFile, 0);
		}
		return result;
	}

	/**
	 * <pre>
	 *   改变FTP服务器的工作路径,
	 *   目录不存在，则创建目录，然后进入目录.
	 * </pre>
	 * @param remoteDir
	 * @throws java.io.IOException
	 */
	private void changeRemoteDir(String remoteDir) throws IOException {
		if (!ftpClient.changeWorkingDirectory(remoteDir)) {
			if(ftpClient.makeDirectory(remoteDir)) {
				ftpClient.changeWorkingDirectory(remoteDir);
			}
		}
	}

	/**
	 * 上传文件到服务器,文件不存在新上传，如果文件存在则继续上传
	 *
	 * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile 本地文件 File句柄，绝对路径
	 * @param remoteSize 远程文件大小
	 * @return
	 * @throws java.io.IOException
	 */
	private UploadStatus continueTransFile(String remoteFile, File localFile,
			long remoteSize) throws IOException {
		UploadStatus status;
		// 显示进度的上传
		long step = localFile.length() / 100;
		long process = 0;
		long localreadbytes = 0L;
		RandomAccessFile raf = new RandomAccessFile(localFile, "r");
		OutputStream out = ftpClient.storeFileStream(new String(remoteFile));//.appendFileStream(new String(remoteFile));
		// 续传
		if (remoteSize > 0) {
			ftpClient.setRestartOffset(remoteSize);
			if (step!=0)
			{
				process = remoteSize / step;
			}
			raf.seek(remoteSize);
			localreadbytes = remoteSize;
		}
		byte[] bytes = new byte[1024];
		int c;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localreadbytes += c;
			if (step!=0)
			{
				if (localreadbytes / step != process) {
					process = localreadbytes / step;
				}
			}
		}
		out.flush();
		raf.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();
		if (remoteSize > 0) {
			status = result ? UploadStatus.Upload_From_Break_Success
					: UploadStatus.Upload_From_Break_Failed;
		} else {
			status = result ? UploadStatus.Upload_New_File_Success
					: UploadStatus.Upload_New_File_Failed;
		}
		return status;
	}
	
	public boolean deleteFile(String filename) {
		boolean flag = true;
		try {
			FTPFile[] files = ftpClient.listFiles(filename);
			if (files.length > 0)
				flag = ftpClient.deleteFile(filename);
		} catch (IOException e) {
			log.info("删除FTP上的文件【"+filename+"】时发生错误：", e);
		}
		return flag;
	}
	
	public void moveFile(String from, String to) {
		try {
			FTPFile[] files = ftpClient.listFiles(from);
			if (files.length > 0) {
				to = to.replace("\\", "/");
				String baseDir = to.substring(0, to.lastIndexOf("/"));
				String file = to.substring(to.lastIndexOf("/") + 1);
				this.changeRemoteDir(baseDir);
				ftpClient.rename(from, file);
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

enum UploadStatus {
    Create_Directory_Fail,   //远程服务器相应目录创建失败
     Create_Directory_Success, //远程服务器闯将目录成功
     Upload_New_File_Success, //上传新文件成功
     Upload_New_File_Failed,   //上传新文件失败
     File_Exits,      //文件已经存在
     Remote_Bigger_Local,   //远程文件大于本地文件
     Upload_From_Break_Success, //断点续传成功
     Upload_From_Break_Failed, //断点续传失败
     Delete_Remote_Faild;   //删除远程文件失败
}

