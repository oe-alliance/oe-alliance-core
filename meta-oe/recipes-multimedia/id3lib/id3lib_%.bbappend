id3lib_do_patch() {
    cd ${S}

    if [ -d ${S}/patches ]; then
        QUILT_PATCHES=${S}/patches quilt pop -a
    fi

    if [ -d ${S}/.pc-${BPN} ]; then
        rm -rf ${S}/.pc
        mv ${S}/.pc-${BPN} ${S}/.pc
        QUILT_PATCHES=${S}/debian/patches quilt pop -a
        rm -rf ${S}/.pc
    fi

    QUILT_PATCHES=${S}/debian/patches quilt push -a
    mv ${S}/.pc ${S}/.pc-${BPN}
}
