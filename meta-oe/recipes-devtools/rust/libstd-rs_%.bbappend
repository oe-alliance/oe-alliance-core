do_install () {
    mkdir -p ${D}${rustlibdir}

    rm -f ${B}/target/${RUST_TARGET_SYS}/${BUILD_DIR}/deps/*.d
    cp -r ${B}/target/${RUST_TARGET_SYS}/${BUILD_DIR}/deps/* ${D}${rustlibdir}
}
