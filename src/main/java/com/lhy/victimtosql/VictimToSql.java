package com.lhy.victimtosql;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lhy.victimtosql.dto.GetGroupNameRandomDTO;
import com.lhy.victimtosql.entity.PublicGroupStructureEntity;
import com.lhy.victimtosql.mapper.PublicGroupStructureEntityMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName VictimToSql
 * @Description 把威胁日志插入sql
 * @Author lihengyu
 * @Date 2020/11/23 17:56
 * @Version 1.0
 */
public class VictimToSql {
    public static void main(String[] args) throws IOException {
        int zkbk =Integer.valueOf(Integer.parseInt(args[4].trim()));
        int limit = Integer.valueOf(Integer.parseInt(args[5].trim()));
        int insertNum = Integer.valueOf(Integer.parseInt(args[3].trim()));
        String isRandomGroupName = args[0];
        String groupName = args[2];
        String filePath = args[1];
        //日志路径和日志名
        File file = new File(filePath);
        if ("1".equals(isRandomGroupName)){
            startWithRandomGroupName(zkbk,limit,insertNum,file);
        }else {
            startWithDesignatedGroupName(groupName,insertNum,file);
        }
      /*  File file = new File("c:/victim_attacker.log");

        if ("1".equals("1")){
            startWithRandomGroupName(2, 3,100,file);
        }else {
            startWithDesignatedGroupName("test",100,file);
        }
*/
    }

    /**
    * @Description //TODO 指定groupname
    * @Param []
    * @return void
    **/
    public static void  startWithDesignatedGroupName(String groupName,int i,File file) throws IOException {
        int count = readBetter(file, groupName, i);
        System.out.println("共插入"+count+"条"+groupName);
    }

    public void run(){

    }

    /**
    * @Description //TODO 随机groupName
    * @Param []
    * @return void
    **/
    public static void  startWithRandomGroupName(int zbzk,int limit,int i,File file) throws IOException {
        List<String> groupNameRandom = getGroupNameRandom(new GetGroupNameRandomDTO(zbzk, limit));

  //      ExecutorService executorService = Executors.newCachedThreadPool();
        for (String groupName : groupNameRandom){

            int count = readBetter(file, groupName, i);
            System.out.println("共插入"+count+"条"+groupName);
        }
    }

    public static List<String> getGroupNameRandom(GetGroupNameRandomDTO dto) throws IOException {
        List<String> list = new ArrayList<String>();

        String resouces = "mybatis_config.xml";
        InputStream in = Resources.getResourceAsStream(resouces);
        //读取配置文件的配置信息，利用SqlSessionFactoryBuilder创建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);

        //利用sqlSessionFactory打开与数据库的会话
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        try {
            //通过sqlSession得到mapper
            PublicGroupStructureEntityMapper mapper = sqlSession.getMapper(PublicGroupStructureEntityMapper.class);

            //调用mapper的方法
            list= mapper.getGroupNameRandom(dto);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            sqlSession.close();
        }
        return list;
    }

    public static int insertBatch(List<PublicGroupStructureEntity> entityList) throws IOException {
        int i = -1;
        String resouces = "mybatis_config.xml";
        InputStream in = Resources.getResourceAsStream(resouces);
        //读取配置文件的配置信息，利用SqlSessionFactoryBuilder创建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);

        //利用sqlSessionFactory打开与数据库的会话
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        try {
            //通过sqlSession得到mapper
            PublicGroupStructureEntityMapper mapper = sqlSession.getMapper(PublicGroupStructureEntityMapper.class);

            //调用mapper的方法
            i = mapper.insertBatch(entityList);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            sqlSession.close();
        }
        return i;
    }

    public static int readBetter(File file,String groupName,int i) throws IOException {

        StringBuffer buffer = new StringBuffer();

        List<JSONObject> JsonList = new ArrayList<JSONObject>();

        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
        // 用1KB的缓冲读取文本文件
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),1024);
        String line = "";
        //把每一行日志的Json存入List
        while((line = reader.readLine()) != null){
            i--;
            if (i<0) {
                break;
            }
            JsonList.add(JSONUtil.parseObj(line));
        }
        reader.close();
        fis.close();


        List<PublicGroupStructureEntity> entityList = new ArrayList<PublicGroupStructureEntity>();
        int insert = 0;

        //遍历list把每一条json变成实体存入mysql
        for (JSONObject obj : JsonList) {
            PublicGroupStructureEntity entity = new PublicGroupStructureEntity();
            entity.setGroupname(groupName);
            entity.setChildId(String.valueOf(obj.get("victim")));
            entity.setChildName(0);
            entity.setChildType("2");
            entity.setSeverType(1L);

            entityList.add(entity);
        }
        insert = insertBatch(entityList);

        return insert;
    }


}
