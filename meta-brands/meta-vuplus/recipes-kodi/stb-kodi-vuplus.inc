do_configure:prepend() {
    cp -av ${WORKDIR}/xbmc-support/gles_init.* ${S}/xbmc/windowing/egl/
}

do_install:append(){
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/xbmc-support/xbmc.helper ${D}${bindir}
    install -m 0755 ${WORKDIR}/xbmc-support/libxbmc_base.so ${D}${libdir}
}

FILES:${PN}:append = "${bindir}/xbmc.helper ${libdir}/libxbmc_base.so"

INSANE_SKIP:${PN} += "file-rdeps"
