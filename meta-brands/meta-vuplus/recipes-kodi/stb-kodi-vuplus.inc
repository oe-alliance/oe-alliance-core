do_configure:prepend() {
    cp -av ${UNPACKDIR}/xbmc-support/gles_init.* ${S}/xbmc/windowing/egl/
    cp -av ${UNPACKDIR}/xbmc-support/libxbmc_base.so ${STAGING_LIBDIR}/
}

do_install:append(){
    install -d ${D}${bindir}
    install -m 0755 ${UNPACKDIR}/xbmc-support/xbmc.helper ${D}${bindir}
    install -m 0755 ${UNPACKDIR}/xbmc-support/libxbmc_base.so ${D}${libdir}
}

FILES:${PN}:append = " ${bindir}/xbmc.helper ${libdir}/libxbmc_base.so"

INSANE_SKIP:${PN} += "file-rdeps"
