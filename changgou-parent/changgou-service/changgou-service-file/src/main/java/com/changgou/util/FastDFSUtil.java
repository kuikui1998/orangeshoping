package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @auther kuikui
 * @create 2020-11-02 10:17
 *  实现FastDFS文件管理
 *      文件上传
 *      文件删除
 *      文件下载
 *      文件信息获取
 *      Storage信息获取
 *      Tracker信息获取
 */
public class FastDFSUtil {
    /**
     * 加载Tracker链接信息
     *
     */
    static {
      try {
          //查找classpath下的文件路径
          String filename = new ClassPathResource("fdfs_client.conf").getPath();
          //加载Tracker链接信息
          ClientGlobal.init(filename);
      }catch (Exception  e){
          e.printStackTrace();
      }
    }

    /**
     * 文件上传
     * @param fastDFSFile
     * @return
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception{
        //附加参数
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author",fastDFSFile.getAuthor());

        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);

        /**
         * /通过StorageClient访问Storage，实现文件上传 并且获取文件上传
         * 1:文件上传的字节数组
         * 2：文件的扩展名  jpg
         * 3：附加参数   地址：北京
         *
         * upload[]
         *      upload[0]:文件上传所储存的Storage 的组的名字 group1
         *      upload[1]:文件存储到Stroage上的文件名字     M00/11/02/01.jpg
         */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        return uploads;

    }

    /**
     * 获取文件信息
     * @param groupName ：文件组名   group1
     * @param remoteFileName    :   文件的存储路径名字   M00/00/00/sfasg.jpg
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);
        //获取文件信息
        FileInfo file_info = storageClient.get_file_info(groupName, remoteFileName);
        return file_info;
    }

    /**
     * 文件下载
     * @param groupName ：文件组名   group1
     * @param remoteFileName    :   文件的存储路径名字   M00/00/00/sfasg.jpg
     * @throws Exception
     */
    public static InputStream downloadFile(String groupName, String remoteFileName) throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);

        //文件下载
        byte[] buffer = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(buffer);
    }

    /**
     * 删除文件
     * @param groupName ：文件组名   group1
     * @param remoteFileName    :   文件的存储路径名字   M00/00/00/sfasg.jpg
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFileName) throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);

        //删除文件
        storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     * 获取Storage信息
     * @return
     */
    public static StorageServer getStorages() throws Exception{
        //创建一个TrackerClient对象，通过TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();

        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     * 获取Storage的IP和端口信息
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws Exception{
        //创建一个TrackerClient对象，通过TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //获取Storage的IP和端口信息
        return trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
    }

    /**
     * 获取Tracker信息
     * @throws Exception
     * @return
     */
    public static String getTrackerInfo() throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();
        //Tracker的IP，HTTP端口
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int tracker_http_port = ClientGlobal.getG_tracker_http_port();//8080
        String url = "http://"+ip+":"+tracker_http_port;
        return url;
    }

    /**
     * 获取Tracker
     * @return
     * @throws Exception
     */
    public static TrackerServer getTrackerServer() throws Exception{
        //创建一个TrackerClient对象，通过TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /**
     * 获取StorageClient
     * @param trackerServer
     * @return
     */
    public static StorageClient getStorageClient(TrackerServer trackerServer){
        //通过TrackerServer的连接信息 可以获取Storage的连接信息，创建storageClient对象存储Storage的连接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }

    public static void main(String[] args) throws Exception{
        //获取文件信息
//        FileInfo fileInfo = getFile("group1", "M00/00/00/wKgWhF-foHGAR9jTAAD7cr0_JoI229.jpg");
//        System.out.println(fileInfo.getSourceIpAddr());
//        System.out.println(fileInfo.getFileSize());

        //文件下载
//        InputStream is = downloadFile("group1", "M00/00/00/wKgWhF-foHGAR9jTAAD7cr0_JoI229.jpg");
//
//        //将文件写入到本地磁盘
//        FileOutputStream os = new FileOutputStream("D:/1.jpg");
//
//        //定义一个缓冲区
//        byte[] buffer = new byte[1024];
//        while (is.read(buffer)!=-1){
//            os.write(buffer);
//        }
//        os.flush();
//        os.close();
//        is.close();

        //删除文件
        //deleteFile("group1", "M00/00/00/wKgWhF-ftMWAcLzYABVFQ5g0vJA975.jpg");

        //获取Storage信息
//        StorageServer storageServer = getStorages();
//        System.out.println(storageServer.getStorePathIndex());
//        System.out.println(storageServer.getInetSocketAddress().getHostString());   //IP信息

        //获取Storage组的IP和端口信息
//        ServerInfo[] groups = getServerInfo("group1", "M00/00/00/wKgWhF-fnMuAfQkKAAD7cr0_JoI069.jpg");
//        for (ServerInfo group : groups) {
//            System.out.println(group.getIpAddr());
//            System.out.println(group.getPort());
//        }

        //获取Tracker的信息
//        System.out.println(getTrackerInfo());

    }

}
