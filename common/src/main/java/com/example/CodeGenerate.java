package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CodeGenerate {

    // ==============================================
    // 只关注这里面的配置就行
    // 代码来源的服务名
    private static final String originalServiceName = "purchase-service".toLowerCase();

    // 代码生成地点的服务名
    private static final String targetServiceImplName = "user-service".toLowerCase();

    // 要生成的数据库表名
    private static final String databaseTableName = "sys_user".toLowerCase();
    // 服务路由地址前缀
    private static final String servicePrefix = "user";
    // ==============================================

    // 公共类
    private static final String commonName = "common".toLowerCase();
    // 原始文件名
    private static final String originalGenerateObjectName = "generate".toLowerCase();
    // 数据库信息
    private static final String databaseUrl = "jdbc:mysql://localhost:3306/warehousing-system?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&allowPublicKeyRetrieval=true";
    private static final String databaseUserName = "root";
    private static final String databasePassword = "1234qwerQWER";

    private static final String servicePage = commonName + "\\src\\main\\java\\com\\example\\service";
    private static final String convertPage = commonName + "\\src\\main\\java\\com\\example\\convert";
    private static final String voPage = commonName + "\\src\\main\\java\\com\\example\\vo";
    private static final String dtoPage = commonName + "\\src\\main\\java\\com\\example\\dto";
    private static final String entityPage = commonName + "\\src\\main\\java\\com\\example\\entity";
    private static final String mapperPage = commonName + "\\src\\main\\java\\com\\example\\mapper";

    // 原始文件路径
    private static final String originalControllerPage = originalServiceName + "\\src\\main\\java\\com\\example\\controller";
    private static final String originalXmlPage = originalServiceName + "\\src\\main\\resources\\mapper";
    private static final String originalServiceImplPage = originalServiceName + "\\src\\main\\java\\com\\example\\service\\impl";
    // 目标文件路径
    private static final String targetControllerPage = targetServiceImplName + "\\src\\main\\java\\com\\example\\controller";
    private static final String targetXmlPage = targetServiceImplName + "\\src\\main\\resources\\mapper";
    private static final String targetServiceImplPage = targetServiceImplName + "\\src\\main\\java\\com\\example\\service\\impl";

    private static final File serviceFile = new File(servicePage + File.separator + originalGenerateObjectName + "Service.java");
    private static final File convertFile = new File(convertPage + File.separator + originalGenerateObjectName + "Convert.java");
    private static final File voFile = new File(voPage + File.separator + originalGenerateObjectName + "VO.java");
    private static final File dtoFile = new File(dtoPage + File.separator + originalGenerateObjectName + "DTO.java");
    private static final File entityFile = new File(entityPage + File.separator + originalGenerateObjectName + ".java");
    private static final File mapperFile = new File(mapperPage + File.separator + originalGenerateObjectName + "Mapper.java");

    private static final File originalControllerFile = new File(originalControllerPage + File.separator + originalGenerateObjectName + "Controller.java");
    private static final File originalXmlFile = new File(originalXmlPage + File.separator + originalGenerateObjectName + "Mapper.xml");
    private static final File originalServiceImplFile = new File(originalServiceImplPage + File.separator + originalGenerateObjectName + "ServiceImpl.java");

    private static final File targetControllerFile = new File(targetControllerPage + File.separator + originalGenerateObjectName + "Controller.java");
    private static final File targetXmlFile = new File(targetXmlPage + File.separator + originalGenerateObjectName + "Mapper.xml");
    private static final File targetServiceImplFile = new File(targetServiceImplPage + File.separator + originalGenerateObjectName + "ServiceImpl.java");

    public static void main(String[] args) {
//        环境跳到主目录
        System.setProperty("user.dir", System.getProperty("user.dir"));
        checkFile();

        List<DatabaseEntity> databaseEntityList = getDatabaseEntity(databaseTableName);
        if (ObjectUtils.isEmpty(databaseEntityList)) {
            System.out.println("数据库字段为空");
            return;
        }
        for (DatabaseEntity databaseEntity : databaseEntityList) {

        }

        System.out.println("执行完成");
    }

    private static void checkFile() {
        for (File file : List.of(serviceFile, convertFile, voFile, dtoFile, entityFile, mapperFile, originalControllerFile, originalXmlFile, originalServiceImplFile)) {
            if (!file.exists()) {
                throw new RuntimeException(file.getAbsoluteFile() + " 不存在");
            }
        }
        for (File file : List.of(targetControllerFile, targetXmlFile, targetServiceImplFile)) {
            if (file.exists()) {
                System.out.println(file.getAbsoluteFile() + " 已存在，是否删除，1删除，其它取消。请输入：");
                if (new Scanner(System.in).nextLine().equals("1")) {
                    if (file.delete()) {
                        System.out.println(file.getAbsoluteFile() + " 删除成功");
                    } else {
                        throw new RuntimeException(file.getAbsoluteFile() + " 删除失败");
                    }
                } else {
                    System.exit(0);
                }
            }
        }
    }


    // 获取数据库字段
    private static List<DatabaseEntity> getDatabaseEntity(String tableName) {
        try (Connection connection = DriverManager.getConnection(databaseUrl, databaseUserName, databasePassword); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("show columns from " + tableName)) {
            return DatabaseEntity.of(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    @AllArgsConstructor
    static class DatabaseEntity {
        private String filed;
        private String type;
        private boolean isNull;
        private String key;
        private String defaultValue;
        private String extra;

        public static List<DatabaseEntity> of(ResultSet resultSet) throws SQLException {
            List<DatabaseEntity> databaseEntityList = new ArrayList<>();
            while (resultSet.next()) {
                databaseEntityList.add(new DatabaseEntity(resultSet.getString("Field"), resultSet.getString("Type"), resultSet.getBoolean("Null"), resultSet.getString("Key"), resultSet.getString("Default"), resultSet.getString("Extra")));
            }
            return databaseEntityList;
        }
    }

}
