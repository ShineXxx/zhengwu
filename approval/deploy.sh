#!/usr/bin/env bash
export LANG="en_US.UTF-8"

echo '===== 环境发布 ===== '

#----------------------------------------------------------------------
# 常量配置信息
#----------------------------------------------------------------------
username='root'

after_build_jar_dir='./target'

# 远程服务器 ip
remote_server_ip='127.0.0.1'

# !确保远程文件夹存在
jar_store_dir='/home/approval'

jar_name='approval-service-0.0.1-SNAPSHOT'

#----------------------------------------------------------------------
# 脚本
#----------------------------------------------------------------------

# 构建项目
echo "正在构建..."
mvn clean package -DskipTests

if [ $? != 0 ]; then
  echo "构建失败"
  exit 1
fi
echo "构建成功"

echo '备份jar'
time=$(date +%Y%m%d%S)
#ssh ${username}@${remote_server_ip} ${jar_store_dir}/run.sh stop ${jar_name}.jar
ssh ${username}@${remote_server_ip} mv ${jar_store_dir}/*.jar ${jar_store_dir}/jar-bak/${jar_name}.jar.${time}

# 将jar 包 scp 值目标服务器指定目录下
# shellcheck disable=SC2144
if [ -f ${after_build_jar_dir}/*.jar ]; then
  echo "正在上传 jar 包..."
  scp ${after_build_jar_dir}/*.jar ${username}@${remote_server_ip}:${jar_store_dir}
  # shellcheck disable=SC2181
  if [ $? != 0 ]; then
    echo "上传失败"
    read
  fi
fi
echo "上传成功"
read
