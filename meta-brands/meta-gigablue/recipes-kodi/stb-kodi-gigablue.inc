do_configure:prepend() {
    cp -av ${UNPACKDIR}/xbmc-support/gles_init.* ${S}/xbmc/windowing/egl/
}

do_install:append(){
}

FILES:${PN}:append = ""
