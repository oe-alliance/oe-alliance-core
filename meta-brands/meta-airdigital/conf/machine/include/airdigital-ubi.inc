IMAGE_CMD:ubi:append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.ubi ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/${KERNEL_FILE}; \
    if [ "${PACKAGE_LIST}" = "1" ]; then \
        cp ${IMAGE_MANIFEST} ${IMAGE_ROOTFS}/usr/lib/package.lst; \
    fi; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/update_bootargs_${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/pq_param.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/baseparam.img; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/logo.img; \
    else \
        cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/logo.img; \
    fi; \
    echo "${IMAGE_NAME}" > ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    zip -r ../${IMAGE_NAME}_usb.zip *; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    cd ..; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}; \
    "