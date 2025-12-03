ssh root@47.109.137.128 'rm -rf /docker/seata/*'
scp -r ./ root@47.109.137.128:/docker/seata/

ssh root@47.109.137.128 '
docker stop seata-server
docker rm seata-server
cd /docker/seata/
echo "启动 Seata Server..."
docker compose down
docker compose up -d
echo "等待服务启动..."
sleep 15

# 检查服务状态
if docker ps | grep -q "seata-server"; then
    echo "✅ Seata Server 启动成功！"
    echo "📊 控制台地址: http://47.109.137.128:7091"
    echo "🔧 TC 服务地址: 47.109.137.128:8091"
    echo "📝 默认账号: seata/seata"

    # 检查健康状态
    echo "检查服务健康状态..."
    if docker exec seata-server curl -s http://localhost:7091/seata/actuator/health > /dev/null 2>&1; then
        echo "✅ 健康检查通过"
    else
        echo "⚠️  健康检查失败，正在查看日志..."
        docker logs seata-server --tail 30
    fi

    # 查看启动日志
    echo "查看最后20行日志:"
    docker logs seata-server --tail 20
else
    echo "❌ Seata Server 启动失败！"
    echo "查看详细日志:"
    docker logs seata-server
    exit 1
fi

echo "🚀 部署完成！"
'

read -p "按任意键继续..."