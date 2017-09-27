DEFAULT_PREFERENCE = "-1"

include gstreamer1.0-plugins-good.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe \
"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+(\.\d+)+))"

SRCREV_base = "a802f5df424685f7af9471c650cc0cd5ef196c0c"
SRCREV_common = "3f4aa969cbe39584a649d98d4cf321d78bd73092"
SRCREV_FORMAT = "base"

SRC_URI = "git://anongit.freedesktop.org/gstreamer/gst-plugins-good;branch=master;name=base \
           git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
"

SRC_URI += "file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
"

S = "${WORKDIR}/git"

GST_VERSION_FULL ="1.13.0.2"
inherit gitpkgv
PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

CFLAGS_append += " -Wno-maybe-uninitialized -Wno-uninitialized "

do_configure_prepend() {
	${S}/autogen.sh --noconfigure
}


PACKAGESPLITFUNCS_append = " handle_mpg_rename "

python handle_mpg_rename () {
    d.setVar('RPROVIDES_gstreamer1.0-plugins-good-lame', 'gstreamer1.0-plugins-ugly-lame')
    d.setVar('RREPLACES_gstreamer1.0-plugins-good-lame', 'gstreamer1.0-plugins-ugly-lame')
    d.setVar('RCONFLICTS_gstreamer1.0-plugins-good-lame', 'gstreamer1.0-plugins-ugly-lame')

    d.setVar('RPROVIDES_gstreamer1.0-plugins-good-mpg123', 'gstreamer1.0-plugins-ugly-mpg123')
    d.setVar('RREPLACES_gstreamer1.0-plugins-good-mpg123', 'gstreamer1.0-plugins-ugly-mpg123')
    d.setVar('RCONFLICTS_gstreamer1.0-plugins-good-mpg123', 'gstreamer1.0-plugins-ugly-mpg123')
}
