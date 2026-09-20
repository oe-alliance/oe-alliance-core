plugin = "MediaPlayer3"

SUMMARY = "${plugin} - A modern audio player for Enigma2 receivers"
DESCRIPTION = "A feature-rich media player for Enigma2 receivers, supporting local audio files, internet radio, podcasts, playlists, and Finnish radio EPG."
AUTHOR = "onni-k"
LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
HOMEPAGE = "https://github.com/onni-k/mediaplayer3"
BUGTRACKER = "https://github.com/onni-k/mediaplayer3/issues"

require conf/python/python3-compileall.inc

inherit gitpkgv allarch

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/onni-k/mediaplayer3.git;branch=main;protocol=https"
SRCREV = "${AUTOREV}"

DEPENDS += "gettext-native"

pluginpath = "/usr/lib/enigma2/python/Plugins/Extensions/${plugin}"

do_install() {
    install -d ${D}${pluginpath}
    cp -r ${S}/src/* ${D}${pluginpath}/
    find ${S}/po/ -maxdepth 1 -type f -name '*.po' | while read po ; do
        ## remove everything before and including the "/"
        filename=${po##*/}
        ## remove everything after and including the "."
        cc=${filename%%.*}
        folder=${D}${pluginpath}/locale/${cc}/LC_MESSAGES
        mkdir -p ${folder}
        msgfmt -o ${folder}/${plugin}.mo ${po}
    done
}

FILES:${PN} = "${pluginpath}/*"
