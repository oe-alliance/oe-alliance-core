SUMMARY = "Dreambox compatibility links"
SECTION = "base"
PRIORITY = "required"

require conf/license/license-gplv2.inc

DEPENDS = " \
       jpeg \
       libdvbsi++ \
       libungif \
       openssl \
       python \
       "

RDEPENDS_${PN} = "\
       jpeg \
       libdvbsi++ \
       libungif \
       libssl \
       libpython2 \
       "       

PR = "r8"

do_install() {
        install -d ${D}${base_libdir}
        ln -sf libgcc_s.so.1 ${D}${base_libdir}/libgcc_s_nof.so.1
        install -d ${D}${libdir}
}

pkg_postinst_${PN}() {
#!/bin/sh
if [ ! -e $D${libdir}/libungif.so.4       ]; then if [ -e $D${libdir}/libgif.so.4         ]; then ln -sf libgif.so.4         $D${libdir}/libungif.so.4       ; fi; fi  
if [ ! -e $D${libdir}/libungif.so.4       ]; then if [ -e $D${libdir}/libungif.so.4.1.3   ]; then ln -sf libungif.so.4.1.3   $D${libdir}/libungif.so.4       ; fi; fi  
if [ ! -e $D${libdir}/libdvbsi++.so.0     ]; then if [ -e $D${libdir}/libdvbsi++.so.1     ]; then ln -sf libdvbsi++.so.1     $D${libdir}/libdvbsi++.so.0     ; fi; fi  
if [ ! -e $D${libdir}/libjpeg.so.62       ]; then if [ -e $D${libdir}/libjpeg.so.8        ]; then ln -sf libjpeg.so.8        $D${libdir}/libjpeg.so.62       ; fi; fi  
if [ ! -e $D${libdir}/libssl.so.0.9.7     ]; then if [ -e $D${libdir}/libssl.so.0.9.8     ]; then ln -sf libssl.so.0.9.8     $D${libdir}/libssl.so.0.9.7     ; fi; fi  
if [ ! -e $D${libdir}/libssl.so.0.9.7     ]; then if [ -e $D${libdir}/libssl.so.1.0.0     ]; then ln -sf libssl.so.1.0.0     $D${libdir}/libssl.so.0.9.7     ; fi; fi  
if [ ! -e $D${libdir}/libpython2.6.so.1.0 ]; then if [ -e $D${libdir}/libpython2.7.so.1.0 ]; then ln -sf libpython2.7.so.1.0 $D${libdir}/libpython2.6.so.1.0 ; fi; fi  
}

PACKAGES = "${PN}"
