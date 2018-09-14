do_configure_prepend() {
    cp -av ${WORKDIR}/xbmc-support/gles_init.* ${S}/xbmc/windowing/egl/
}

do_install_append(){
}

FILES_${PN}_append = ""
