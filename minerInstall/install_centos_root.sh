#!/bin/sh

# 创建目录
if [ ! -d "/root/download" ]; then
  mkdir -p /root/download
fi
if [ ! -d "/root/opt/BDCN_sh" ]; then
  mkdir -p /root/opt/BDCN_sh
fi

# 依赖
yum install -y tar wget

# 下载
cd /root/download
wget https://github.com/ItTianYuStudio/BcdnWatcher/raw/master/minerInstall/M_BerryMiner_ubuntu_v1_0.tar.gz

# 解压
tar -zxvf /root/download/M_BerryMiner_ubuntu_v1_0.tar.gz -C /root/opt

# 添加执行权限
cd /root/opt/M_BerryMiner_ubuntu_v1_0/server
chmod 755 bcdn
chmod 755 ./bin/bcdn_server

# 守护程序
cd /root/opt/BDCN_sh
wget https://raw.githubusercontent.com/ItTianYuStudio/BcdnWatcher/master/minerInstall/daemon_root.sh
chmod +x daemon.sh

# 开机自动启动脚本
echo -e '#!/bin/sh\n  cd /root/opt/BDCN_sh\n nohup ./daemon.sh > /root/opt/BDCN_sh/nohup.out' > /root/opt/BDCN_sh/auto_startup.sh
chmod +x /root/opt/BDCN_sh/auto_startup.sh
chmod +x /etc/rc.d/rc.local
echo "/root/opt/BDCN_sh/auto_startup.sh" >> /etc/rc.d/rc.local

# 输入挖矿码
#read -p "请输入挖矿码(Please input your miner code):" code
#echo "$1" > /root/opt/M_BerryMiner_ubuntu_v1_0/server/conf/code.txt 

# 启动守护程序
cd /root/opt/BDCN_sh
nohup ./daemon.sh &

echo "安装成功 (install success)"