require gst-plugins.inc

LICENSE = "GPLv2+ & LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=622921ffad8cb18ab906c56052788a3f"

DEPENDS += "gst-plugins-base libid3tag libmad mpeg2dec liba52 lame libcdio opencore-amr"

EXTRA_OECONF += "--enable-orc"

SRCREV = "${AUTOREV}"
GIT_PV = ""

inherit autotools pkgconfig gettext git-project

#SRC_URI = "git://anongit.freedesktop.org/gstreamer/${PN};protocol=git;branch=0.10"
SRC_URI = "git://anongit.freedesktop.org/git/gstreamer/${PN};protocol=http;branch=0.10"

SRC_URI += " \
        file://orc.m4-fix-location-of-orcc-when-cross-compiling.patch \
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
    rm ${S}/m4/lib-link.m4 || true
    # manually provide remove-potcdate.sin, while our intltoolize does not install it
    cp ${STAGING_DATADIR_NATIVE}/gettext/po/remove-potcdate.sin ${S}/po/
    touch ${S}/ABOUT-NLS
}

require mips-only.inc
