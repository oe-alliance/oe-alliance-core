SUMMARY = "DVD navigation multimeda library"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://README;beginline=42;endline=43;md5=3f2e6972567beedaa901b809725027ff \
                    file://src/dvdnav.c;beginline=1;endline=19;md5=8270661e7e05a78e14714e0fb3048b12"
DEPENDS = "libdvdread"
PV = "4.2.0+svnr${SRCPV}"
PR = "r1"

SRC_URI = "svn://svn.mplayerhq.hu/dvdnav/trunk;module=${PN} \
           file://0001-dvdnavmini.pc-link-against-libdvdnavmini.patch"

SRCREV = "1243"
S = "${WORKDIR}/${PN}"

inherit autotools lib_package binconfig pkgconfig

FILES_${PN} = "${libdir}/${PN}${SOLIB}"

python populate_packages_prepend() {
        description = bb.data.expand('${DESCRIPTION}', d)
        libdir = bb.data.expand('${libdir}', d)
        do_split_packages(d, libdir, '^lib(.*)\.so\..*', 'lib%s', description + ' (%s)', extra_depends='', allow_links=True)
}
