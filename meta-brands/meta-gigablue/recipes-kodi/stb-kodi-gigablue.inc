do_configure:prepend() {
    cp -av ${WORKDIR}/xbmc-support/gles_init.* ${S}/xbmc/windowing/egl/
}

do_install:append(){
}

FILES:${PN}:append = ""
