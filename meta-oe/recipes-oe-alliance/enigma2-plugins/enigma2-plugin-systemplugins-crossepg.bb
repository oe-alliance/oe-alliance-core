DESCRIPTION = "Handle your EPG on enigma2 from various sources (opentv, xmltv, custom sources)"
HOMEPAGE = "https://github.com/oe-alliance/e2openplugin-CrossEPG"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=4fbd65380cdd255951079008b364516c"

DEPENDS += "curl libxml2 ${PYTHON_PN} swig-native zlib"
RDEPENDS_${PN} += "enigma2 libcurl ${PYTHON_PN}-core ${PYTHON_PN}-compression ${PYTHON_PN}-backports-lzma xz"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv ${PYTHON_PN}native

SRCREV = "${AUTOREV}"
PV = "0.8.7+gitr${SRCPV}"
PKGV = "0.8.7+gitr${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-CrossEPG.git;protocol=git;branch=dev \
        file://fix-build-with-fno-common.patch"

inherit ${PYTHON_PN}-dir

S = "${WORKDIR}/git"

CFLAGS_append = " -I${STAGING_INCDIR}/libxml2/ -I${STAGING_INCDIR}/${PYTHON_DIR}/"
CFLAGS_append = " ${@bb.utils.contains('BRAND_OEM', 'xtrend', ' -DNO_DVB_POLL' , '', d)}"
CFLAGS_append = " ${@bb.utils.contains('BRAND_OEM', 'xp', ' -DNO_DVB_POLL' , '', d)}"
CFLAGS_append = " ${@bb.utils.contains('BRAND_OEM', 'skylake', ' -DNO_DVB_POLL' , '', d)}"
CFLAGS_append = " ${@bb.utils.contains('BRAND_OEM', 'gfutures', ' -DNO_DVB_POLL' , '', d)}"
CFLAGS_append = " ${@bb.utils.contains('BRAND_OEM', 'formuler', ' -DNO_DVB_POLL' , '', d)}"
CFLAGS_append = " ${@bb.utils.contains('BRAND_OEM', 'airdigital', ' -DNO_DVB_POLL' , '', d)}"

do_compile() {
    echo ${PV} > ${S}/VERSION
    oe_runmake SWIG="swig"
}

do_install() {
    oe_runmake 'D=${D}' install
    mv ${D}/usr/crossepg/libcrossepg.so ${D}${libdir}/
}

pkg_postrm_${PN}() {
    rm -fr ${libdir}/enigma2/python/Plugins/SystemPlugins/CrossEPG > /dev/null 2>&1
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

ALLOW_EMPTY_${PN} = "1"
FILES_${PN}_append = " /usr/crossepg ${libdir}/libcrossepg.so ${libdir}/${PYTHON_DIR}"
FILES_${PN}-src_append = " ${libdir}/${PYTHON_DIR}/crossepg.py"
FILES_${PN}-dbg_append = " /usr/crossepg/scripts/mhw2epgdownloader/.debug /usr/crossepg/scripts/mhw2epgdownloader/.debug"
FILES_SOLIBSDEV = ""

INSANE_SKIP_${PN} += "already-stripped ldflags"

do_package_qa[noexec] = "1"
