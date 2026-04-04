SUMMARY = "Enigma2 Plugin You WorldCam from site and youtube playlists"
HOMEPAGE = "https://www.linuxsat-support.com"
MAINTAINER = "lululla"
SECTION = "base"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "ffmpeg gstplayer exteplayer3 enigma2-plugin-systemplugins-serviceapp streamlink enigma2-plugin-extensions-streamlinkwrapper enigma2-plugin-extensions-ytdlpwrapper enigma2-plugin-extensions-ytdlwrapper gstreamer1.0-plugins-bad gstreamer1.0-plugins-ugly gstreamer1.0-libav python3-youtube-dl python3-yt-dlp python3-requests"

inherit gittag

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

SRC_URI = "git://github.com/Belfagor2005/WorldCam.git;branch=main;protocol=https"

FILES:${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}
    cp --no-preserve=ownership --recursive ${S}/usr/lib/* ${D}${libdir}/
}

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

pkg_postinst:${PN} () {
#!/bin/sh
mkdir -p /etc/enigma2
echo -e "# Netscape HTTP Cookie File\n.youtube.com\tTRUE\t/\tTRUE\t2147483647\tCONSENT\tYES+cb.20210615-14-p0.it+FX+294\n.youtube.com\tTRUE\t/\tTRUE\t2147483647\tPREF\tf1=50000000" > /etc/enigma2/yt_cookies.txt
if [ -d "/usr/lib/enigma2/python/Plugins/Extensions/WorldCam" ]; then
    chmod -R 755 "/usr/lib/enigma2/python/Plugins/Extensions/WorldCam"
fi
exit 0
}
