
## 本矿机安装脚本优势 ##

- 自动安装
- 自带守护进程
- 开机自动启动


## Linux 矿机安装教程 ##

首先你得有一台装了 Linux 系统的电脑，可以是 云服务器，也可以是虚拟机装的。

系统可以是 Ubuntu，也可以是 Centos，其他 Linux 系统不清楚，请自己测试。


根据自己系统类别，选择复制一个复制命令，在 shell 命令行中执行，根据提示输入挖矿码，然后回车即可

#### Ubuntu ####

```
curl -o- https://raw.githubusercontent.com/ItTianYuStudio/BcdnWatcher/master/minerInstall/install_ubuntu.sh | bash
```

#### Centos ####


```
curl -o- https://raw.githubusercontent.com/ItTianYuStudio/BcdnWatcher/master/minerInstall/install_centos.sh | bash
```

## 输入挖矿码 ##

```
echo "这里替换成你的挖矿码" > ~/opt/M_BerryMiner_ubuntu_v1_0/server/conf/code.txt 
```

挖矿码输入后（守护进程会自动重启矿机，无需手动做任何操作），等待 1 分钟左右，检查是否已经上线成功。


## 开机启动 ##

上面脚本里面没添加开机启动，因为不支持相对路径
如果你是 root 用户登录的，则可以手动执行下面命令，来实现开机自动执行守护程序

非 root 用户没权限添加开机启动的
```
echo -e '#!/bin/sh\n  cd /root/opt/BDCN_sh\n nohup ./daemon.sh > /root/opt/BDCN_sh/nohup.out' > /root/opt/BDCN_sh/auto_startup.sh
chmod +x /root/opt/BDCN_sh/auto_startup.sh
chmod +x /etc/rc.d/rc.local
echo "/root/opt/BDCN_sh/auto_startup.sh" >> /etc/rc.d/rc.local
```


## 代安装矿机服务 ##

针对小白玩家，可能连什么是 shell 都不清楚，完全不会动手的，本人提供代安装矿机服务。
但作为程序狗，工作特别忙，为了避免过多人找我，象征性收取 10 元代安装费。

为了节省双方时间，请在联系我之前准备好远程连接服务。

- 如果是云服务器，直接把 帐号密码发我，我连接后帮忙安装。
- 如果是虚拟机，请下载好 TeamViewer, [点这里下载](https://download.teamviewer.com/download/TeamViewer_Setup.exe)。 运行后，把远程连接的 id 和 密码发我，我代为远程操作。


#### 联系方式 ####
- qq 1125316526 
- 微信 ittianyu

