package com.youdai.daichao.framework.config;


/**
 * druid 配置多数据源
 * 
 * @author user-xmp
 */
//@Configuration
//public class DruidConfig
//{
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.master")
//    public DataSource masterDataSource()
//    {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.slave")
//    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
//    public DataSource slaveDataSource()
//    {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "dynamicDataSource")
//    @Primary
//    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource)
//    {
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
//        targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
//        return new DynamicDataSource(masterDataSource, targetDataSources);
//    }
//}
