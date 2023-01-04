SUMMARY = "Dreambox compatibility links"
SECTION = "base"
PRIORITY = "required"

require conf/license/license-gplv2.inc

DEPENDS = " \
       jpeg \
       libdvbsi++ \
       openssl \
       ${PYTHON_PN} \
       "

RDEPENDS:${PN} = "\
       jpeg \
       libdvbsi++ \
       libssl \
       "       

PR = "r9"

do_install() {
        install -d ${D}${base_libdir}
        ln -sf libgcc_s.so.1 ${D}${base_libdir}/libgcc_s_nof.so.1
        install -d ${D}${libdir}
}

pkg_postinst:${PN}() {
#!/bin/sh
if [ ! -e $D${libdir}/libdvbsi++.so.0     ]; then if [ -e $D${libdir}/libdvbsi++.so.1     ]; then ln -sf libdvbsi++.so.1     $D${libdir}/libdvbsi++.so.0     ; fi; fi  
if [ ! -e $D${libdir}/libjpeg.so.62       ]; then if [ -e $D${libdir}/libjpeg.so.8        ]; then ln -sf libjpeg.so.8        $D${libdir}/libjpeg.so.62       ; fi; fi  
if [ ! -e $D${libdir}/libssl.so.0.9.7     ]; then if [ -e $D${libdir}/libssl.so.0.9.8     ]; then ln -sf libssl.so.0.9.8     $D${libdir}/libssl.so.0.9.7     ; fi; fi  
if [ ! -e $D${libdir}/libssl.so.0.9.7     ]; then if [ -e $D${libdir}/libssl.so.1.0.0     ]; then ln -sf libssl.so.1.0.0     $D${libdir}/libssl.so.0.9.7     ; fi; fi  
}

PACKAGES = "${PN}"
