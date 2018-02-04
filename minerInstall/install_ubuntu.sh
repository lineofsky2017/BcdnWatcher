#!/bin/sh

# 创建目录
if [ ! -d "~/download" ]; then
  mkdir -p ~/download
fi
if [ ! -d "~/opt/BDCN_sh" ]; then
  mkdir -p ~/opt/BDCN_sh
fi

# 依赖
apt-get install -y tar wget

# 下载
cd ~/download
wget https://github.com/ItTianYuStudio/BcdnWatcher/raw/master/minerInstall/M_BerryMiner_ubuntu_v1_0.tar.gz

# 解压
tar -zxvf ~/download/M_BerryMiner_ubuntu_v1_0.tar.gz -C ~/opt

# 添加执行权限
cd ~/opt/M_BerryMiner_ubuntu_v1_0/server
chmod 755 bcdn
chmod 755 ./bin/bcdn_server

# 守护程序
cd ~/opt/BDCN_sh
wget https://raw.githubusercontent.com/ItTianYuStudio/BcdnWatcher/master/minerInstall/daemon.sh
chmod +x daemon.sh

# 开机自动启动脚本
echo -e '#!/bin/sh\n  cd ~/opt/BDCN_sh\n nohup ./daemon.sh > ~/opt/BDCN_sh/nohup.out' > ~/opt/BDCN_sh/auto_startup.sh
chmod +x ~/opt/BDCN_sh/auto_startup.sh
chmod +x /etc/rc.d/rc.local
echo "~/opt/BDCN_sh/auto_startup.sh" >> /etc/rc.d/rc.local

# 输入挖矿码
#read -p "请输入挖矿码(Please input your miner code):" code
#echo "$code" > ~/opt/M_BerryMiner_ubuntu_v1_0/server/conf/code.txt 

# 启动守护程序
cd ~/opt/BDCN_sh
nohup ./daemon.sh &

echo "安装成功 (install success)"