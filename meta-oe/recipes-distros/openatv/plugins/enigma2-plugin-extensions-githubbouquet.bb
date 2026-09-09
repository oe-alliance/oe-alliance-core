SUMMARY = "Automatically manage and sync bouquet lists from GitHub"
DESCRIPTION = "An automated GitHub bouquet and EPG data synchronization tool for Enigma2 receivers."
HOMEPAGE = "https://github.com/mathlabroom/Github-bouquet"
SECTION = "multimedia"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=35ffb0cb3298a287a9161a0eb1e5ca11"

# 核心勾连：让 openATV 官方服务器每天半夜自动爬你的独立仓库，永远保持最新
SRC_URI = "git://github.com/mathlabroom/Github-bouquet.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

# 继承 openatv 插件的标准打包规则
inherit packagegroup

do_compile() {
    # 纯 Python 插件，无需交叉编译，原地跳过
    :
}

do_install() {
    # 1. 自动把源码根目录下的 usr 文件夹，完整搬运到固件的安装树中
    cp -rp ${S}/usr ${D}/
    
    # 2. 自动把你的 CONTROL 脚本（如 postinst）打包进 ipk 架构中
    install -d ${D}/CONTROL
    if [ -f ${S}/CONTROL/postinst ]; then
        install -m 0755 ${S}/CONTROL/postinst ${D}/CONTROL/
    fi
}

# 声明打包包含的所有文件路径
FILES:${PN} = " \
    /usr/lib/enigma2/python/Plugins/Extensions/GitHubBouquet/* \
    /CONTROL/* \
"

# 确保兼容性
RDEPENDS:${PN} = "python3-core"
