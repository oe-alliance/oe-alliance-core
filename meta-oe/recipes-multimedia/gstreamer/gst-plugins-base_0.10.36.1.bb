require gst-plugins.inc

LICENSE = "GPLv2+ & LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3 \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=622921ffad8cb18ab906c56052788a3f \
                    file://COPYING.LIB;md5=55ca817ccb7d5b5b66355690e9abc605 \
                    file://gst/ffmpegcolorspace/utils.c;beginline=1;endline=20;md5=9c83a200b8e597b26ca29df20fc6ecd0"

DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'virtual/libx11 libxv', '', d)}"
DEPENDS += "alsa-lib freetype liboil libogg libvorbis libtheora avahi util-linux tremor orc orc-native pango"
DEPENDS += "gstreamer"

SRCREV = "${AUTOREV}"
PR = "r18"
GIT_PV = ""

inherit autotools pkgconfig gettext git-project

#SRC_URI = "git://anongit.freedesktop.org/gstreamer/${PN};protocol=git;branch=0.10"
SRC_URI = "git://anongit.freedesktop.org/git/gstreamer/${PN};protocol=http;branch=0.10"

SRC_URI += " \
        file://orc.m4-fix-location-of-orcc-when-cross-compiling.patch \
        file://disable-vorbis-encoder.patch \
        file://gst-plugins-base-tremor.patch \
        file://configure.ac-fix-subparse-plugin.patch \
        file://0035-playbin2-add-custom-user-agent-property.patch \
        ${@bb.utils.contains('MACHINE_BRAND', 'Dreambox', '', 'file://revert-0dfdd9186e143daa568521c4e55c9923e5cbc466.patch', d)} \
"

do_common_update() {
    sed  's!git://anongit.freedesktop.org/gstreamer/common!http://anongit.freedesktop.org/git/gstreamer/common.git!' -i ${S}/.git/config
    sed  's!git://anongit.freedesktop.org/gstreamer/common!http://anongit.freedesktop.org/git/gstreamer/common.git!' -i ${S}/.gitmodules
    cd ${S}
    # Make sure we have common
    if test ! -f common/gst-autogen.sh;
    then
        echo "+ Setting up common submodule"
        git submodule init
    fi
    git submodule update

    # source helper functions
    if test ! -f common/gst-autogen.sh;
    then
        echo There is something wrong with your source tree.
        echo You are missing common/gst-autogen.sh
        exit 1
    fi
    . common/gst-autogen.sh
    # install pre-commit hook for doing clean commits
    if test ! \( -x .git/hooks/pre-commit -a -L .git/hooks/pre-commit \);
    then
        rm -f .git/hooks/pre-commit
        ln -s ../../common/hooks/pre-commit.hook .git/hooks/pre-commit
    fi

    # GNU gettext automake support doesn't get along with git.
    # https://bugzilla.gnome.org/show_bug.cgi?id=661128
    autopoint || touch config.rpath
    touch -t 200001010000 po/gst-plugins-base-0.10.pot
}
addtask common_update after do_unpack before do_patch

do_configure_prepend() {
    # This m4 file contains nastiness which conflicts with libtool 2.2.2
    rm -f ${S}/m4/lib-link.m4
    # manually provide remove-potcdate.sin, while our intltoolize does not install it
    cp ${STAGING_DATADIR_NATIVE}/gettext/po/remove-potcdate.sin ${S}/po/
    touch ${S}/ABOUT-NLS
}

do_package_qa() {
}

require mips-only.inc
FILES_${PN} += "${datadir}/${BPN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"
