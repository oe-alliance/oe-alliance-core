SUMMARY = "GStreamer multimedia framework"
SUMMARY = "GStreamer is a multimedia framework for encoding and decoding video and sound. \
It supports a wide range of formats including mp3, ogg, avi, mpeg and quicktime."
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=55ca817ccb7d5b5b66355690e9abc605 \
                    file://gst/gst.h;beginline=1;endline=21;md5=8e5fe5e87d33a04479fde862e238eaa4"
DEPENDS = "glib-2.0 libxml2 bison-native flex-native"

SRCREV = "${AUTOREV}"
PR = "r11"
GIT_PV = ""

RRECOMMENDS_${PN}_qemux86    += "kernel-module-snd-ens1370 kernel-module-snd-rawmidi"
RRECOMMENDS_${PN}_qemux86-64 += "kernel-module-snd-ens1370 kernel-module-snd-rawmidi"

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES_${PN} += " ${libdir}/gstreamer-0.10/*.so"
FILES_${PN}-dev += " ${libdir}/gstreamer-0.10/*.la ${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += " ${libdir}/gstreamer-0.10/.debug/ ${libexecdir}/gstreamer-0.10/.debug/"

GSTREAMER_DEBUG ?= "--disable-debug"
EXTRA_OECONF = "--disable-docs-build --disable-dependency-tracking --with-check=no --disable-examples --disable-tests --disable-valgrind ${GSTREAMER_DEBUG}"

inherit autotools pkgconfig gettext git-project
SRC_URI = "git://anongit.freedesktop.org/gstreamer/${PN};protocol=git;branch=0.10"

SRC_URI += " \
    file://check_fix.patch \
    file://gst-inspect-check-error.patch \
    file://multiqueue-sparsestreams.patch \
    file://inputselector-cache-fix.patch \
"

do_common_update() {
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
    rm ${S}/m4/lib-link.m4 || true
}

require mips-only.inc
FILES_${PN} += " ${libdir}/gstreamer-0.10/*.so"
FILES_${PN}-dev += " ${libdir}/gstreamer-0.10/*.la ${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += " ${libdir}/gstreamer-0.10/.debug/ ${libexecdir}/gstreamer-0.10/.debug/"
