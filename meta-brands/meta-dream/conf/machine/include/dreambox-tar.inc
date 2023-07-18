IMAGE_FSTYPES =+ "tar.bz2"
IMAGEDIR = "${MACHINE}"

MTD_KERNEL = "mtd3"
MTD_ROOTFS = "sda3"
KERNEL_FILE = "N/A"
ROOTFS_FILE = "rootfs.tar.bz2"

IMAGE_CMD:tar:prepend = " ; \
	mkdir -p ${IMAGE_ROOTFS}/usr/lib; \
	if [ "${PACKAGE_LIST}" = "1" ]; then \
        cp ${IMAGE_MANIFEST} ${IMAGE_ROOTFS}/usr/lib/package.lst; \
    fi; \
    "

IMAGE_CMD:tar:append = " ; \
    cd ${DEPLOY_DIR_IMAGE}; \
    rm -f *.rootfs.manifest; \
    rm -f .manifest; \
    "

CONVERSION_CMD:bz2:append = ";\
	cd ${DEPLOY_DIR_IMAGE}; \
	mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
	mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.tar.bz2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar.bz2; \
	echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
	echo "dummy file dont delete" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
	zip ${IMAGE_NAME}_flash.zip ${IMAGEDIR}/*; \
	rm -Rf ${IMAGEDIR}; \
	cd ${IMGDEPLOYDIR}; \
"