SUMMARY = "Kodi LCDproc information service"
DESCRIPTION = "Official Kodi service which publishes player, media and navigation information through the LCDproc protocol."
HOMEPAGE = "https://github.com/herrnst/script.xbmc.lcdproc"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=fa22e16ebbe6638b2bd253338fbded9f"

SRC_URI = "git://github.com/herrnst/script.xbmc.lcdproc.git;branch=master;protocol=https"
SRCREV = "a64470ac8e87acb1e7b9d2a07c92bac29dee7aac"

inherit allarch

RDEPENDS:${PN} = "python3-core stb-lcdd"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${datadir}/kodi/addons/script.xbmc.lcdproc
    cp -R --no-preserve=ownership ${S}/* ${D}${datadir}/kodi/addons/script.xbmc.lcdproc/
}

FILES:${PN} = "${datadir}/kodi/addons/script.xbmc.lcdproc"
