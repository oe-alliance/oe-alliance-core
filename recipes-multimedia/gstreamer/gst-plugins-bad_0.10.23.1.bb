require recipes-multimedia/gstreamer/gst-plugins.inc

LICENSE = "GPLv2+ & LGPLv2+ & LGPLv2.1+ "
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3 \
                    file://gst/tta/filters.h;beginline=12;endline=29;md5=629b0c7a665d155a6677778f4460ec06 \
                    file://COPYING.LIB;md5=55ca817ccb7d5b5b66355690e9abc605 \
                    file://gst/tta/crc32.h;beginline=12;endline=29;md5=71a904d99ce7ae0c1cf129891b98145c"

DEPENDS += "libmusicbrainz tremor curl libmms"
DEPENDS += "gst-plugins-base"
CONFLICTS = "librsvg"

PR = "r3"
GIT_PV = ""

SRCREV = "fb0d8700e71c4a6569ba023d16201087aec119fd"

EXTRA_OECONF += "--disable-examples --disable-experimental --disable-sdl --disable-cdaudio --disable-directfb \
                 --disable-vdpau --disable-apexsink --enable-orc --disable-mpeg2enc --disable-mplex --disable-rsvg --disable-uvch264"

ARM_INSTRUCTION_SET = "arm"

SRC_URI = "git://anongit.freedesktop.org/gstreamer/${PN}"

SRC_URI += " \
        file://0003-mpegpsdemux_speedup.diff.patch \
        file://0004-mpegdemux-compile-fixes.patch \
        file://0005-hlsdemux-locking-fixes.patch \
        file://0006-hlsdemux-backport.patch \
        file://orc.m4-fix-location-of-orcc-when-cross-compiling.patch \
"

inherit autotools pkgconfig gettext git-project

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
