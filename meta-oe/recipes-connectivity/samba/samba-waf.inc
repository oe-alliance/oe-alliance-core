inherit qemu

#cross_compile cannot use preforked process, since fork process earlier than point subproces.popen
#to cross Popen
export WAF_NO_PREFORK="yes"

# Three methods for waf cross compile:
# 1. answers:
#    Only --cross-answers - try the cross-answers file, and if
#    there's no corresponding answer, add to the file and mark
#    the configure process as unfinished.
# 2. exec:
#    Only --cross-execute - get the answer from cross-execute,
#    an emulator (qemu) is used to run cross-compiled binaries.
# 3. both:
#    (notes: not supported in lower version of some packages,
#     please check buildtools/wafsamba/samba_cross.py in the
#     package source)
#    Try the cross-answers file first, and if there is no
#    corresponding answer, use cross-execute to get an answer,
#    and add that answer to the file.
#
# The first one is preferred since it may fail with 2 or 3 if
# the target board is not suported by qemu, but we can use 2 or 3
# to help generate the cross answer when adding new board support.
CROSS_METHOD ?= "answer"

export COMPILE_ET="${STAGING_DIR_NATIVE}${bindir}/compile_et"
export ASN1_COMPILE="${STAGING_DIR_NATIVE}${bindir}/asn1_compile"

do_configure() {
    perl -i -pe 's#lp_private_dir#lp_pid_directory#' ${S}/source3/lib/messages.c

    # Prepare the cross-answers file
    WAF_CROSS_ANSWERS_PATH="${THISDIR}/waf-cross-answers"
    CROSS_ANSWERS="${B}/cross-answers-${TARGET_ARCH}.txt"
    if [ -e ${CROSS_ANSWERS} ]; then
        rm -f ${CROSS_ANSWERS}
    fi
    echo 'Checking uname machine type: "${TARGET_ARCH}"' >> ${CROSS_ANSWERS}
    echo 'Checking uname release type: "${OLDEST_KERNEL}"' >> ${CROSS_ANSWERS}
    cat ${WAF_CROSS_ANSWERS_PATH}/cross-answers-${TARGET_ARCH}.txt >> ${CROSS_ANSWERS}

    qemu_binary="${@qemu_target_binary(d)}"
    if [ "${qemu_binary}" = "qemu-allarch" ]; then
        qemu_binary="qemuwrapper"
    fi

    libdir_qemu="${STAGING_DIR_HOST}/${libdir}"
    base_libdir_qemu="${STAGING_DIR_HOST}/${base_libdir}"

    CROSS_EXEC="${qemu_binary} \
                ${QEMU_OPTIONS} \
                -L ${STAGING_DIR_HOST} \
                -E LD_LIBRARY_PATH=${libdir_qemu}:${base_libdir_qemu}"

    export BUILD_ARCH=${BUILD_ARCH}
    export HOST_ARCH=${HOST_ARCH}
    export STAGING_LIBDIR=${STAGING_LIBDIR}
    export STAGING_INCDIR=${STAGING_INCDIR}
    export PYTHONPATH=${STAGING_DIR_HOST}${PYTHON_SITEPACKAGES_DIR}
    export PYTHON_CONFIG=${STAGING_EXECPREFIXDIR}/python-target-config/python3-config


    CONFIG_CMD="./configure ${CONFIGUREOPTS} ${EXTRA_OECONF} --cross-compile"
#    CONFIG_CMD="./buildtools/bin/waf configure ${CONFIGUREOPTS} ${EXTRA_OECONF} --cross-compile"
    if [ "${CROSS_METHOD}" = "answer" ]; then
        ${CONFIG_CMD} --cross-answers="${CROSS_ANSWERS}"
    elif [ "${CROSS_METHOD}" = "exec" ]; then
        ${CONFIG_CMD} --cross-exec="${CROSS_EXEC}"
    elif [ "${CROSS_METHOD}" = "both" ]; then
        ${CONFIG_CMD} --cross-answers="${CROSS_ANSWERS}" --cross-exec="${CROSS_EXEC}"
    else
        echo "ERROR: ${CROSS_METHOD} is not valid for cross-compile!"
        exit 1
    fi
}
