do_configure_prepend() {
    cp -av ${WORKDIR}/xbmc-support/gles_init.* ${S}/xbmc/windowing/egl/
}

do_install_append(){
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/xbmc-support/xbmc.helper ${D}${bindir}
    install -m 0755 ${WORKDIR}/xbmc-support/libxbmc_base.so ${D}${libdir}
}

FILES_${PN}_append = "${bindir}/xbmc.helper ${libdir}/libxbmc_base.so"

INSANE_SKIP_${PN} += "file-rdeps"
