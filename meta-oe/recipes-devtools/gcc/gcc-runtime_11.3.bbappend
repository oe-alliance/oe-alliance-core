do_configure () {
    export CXX="${CXX} -nostdinc++ -nostdlib++"
    for d in libgcc ${RUNTIMETARGET}; do
        echo "Configuring $d"
        rm -rf ${B}/${TARGET_SYS}/$d/
        mkdir -p ${B}/${TARGET_SYS}/$d/
        cd ${B}/${TARGET_SYS}/$d/
        chmod a+x ${S}/$d/configure
        relpath=${@os.path.relpath("${S}/$d", "${B}/${TARGET_SYS}/$d")}
        $relpath/configure ${CONFIGUREOPTS} ${EXTRA_OECONF}
        if [ "$d" = "libgcc" ]; then
            (cd ${B}/${TARGET_SYS}/libgcc; oe_runmake enable-execute-stack.c unwind.h md-unwind-support.h sfp-machine.h gthr-default.h)
        fi
    done
}

do_check() {
    export DEJAGNU="${WORKDIR}/dejagnu/site.exp"

    # HACK: this works around the configure setting CXX with -nostd* args
    sed -i 's/-nostdinc++ -nostdlib++//g' $(find ${B} -name testsuite_flags | head -1)

    if [ "${TOOLCHAIN_TEST_TARGET}" = "user" ]; then
        # qemu user has issues allocating large amounts of memory
        export G_SLICE=always-malloc
        # no test should need more that 10G of memory, this prevents tests like pthread7-rope from leaking memory
        ulimit -m 4194304
        ulimit -v 10485760
    fi

    oe_runmake -i ${MAKE_CHECK_TARGETS} RUNTESTFLAGS="${MAKE_CHECK_RUNTESTFLAGS}"
}
