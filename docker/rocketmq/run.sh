SERVER_IP="47.109.137.128"
SERVER_USER="root"
COMPOSE_CMD="docker compose"

DEPLOY_PATH="/docker/rocketmq"
SERVER_NAME="rocketmq"

if [ -z "${DEPLOY_PATH}" ] || [ "${DEPLOY_PATH}" = "/" ]; then
    echo -e "错误：DEPLOY_PATH 变量不能为空或根目录！"
    read -p "请按任意键结束"
    exit 1
fi

echo "创建远程目录..."

ssh ${SERVER_USER}@${SERVER_IP} "
rm -rf ${DEPLOY_PATH}
mkdir -p ${DEPLOY_PATH}
"
echo "复制文件..."
scp -r ./ ${SERVER_USER}@${SERVER_IP}:${DEPLOY_PATH}

echo "启动..."
ssh ${SERVER_USER}@${SERVER_IP} "
 if ! command -v docker &> /dev/null; then
    echo 'Docker 未安装，请先安装 Docker'
    read -p '请按任意键结束'
    exit 1
fi
docker stop ${SERVER_NAME}
docker rm ${SERVER_NAME}
cd ${DEPLOY_PATH}
echo '启动...'
${COMPOSE_CMD} down
${COMPOSE_CMD} up -d
echo '等待启动...'
docker logs -f ${SERVER_NAME}
"