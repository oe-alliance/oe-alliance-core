inherit image_types

IMAGE_CMD_jffs2.nfi = " \
    mkfs.jffs2 \
        --root=${IMAGE_ROOTFS}/boot \
        --compression-mode=none \
        --output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
        ${EXTRA_IMAGECMD}; \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    mkfs.jffs2 \
        --root=${IMAGE_ROOTFS} \
        --disable-compressor=lzo \
        --compression-mode=size \
        --output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
        ${EXTRA_IMAGECMD}; \
    ${DREAMBOX_BUILDIMAGE} \
        --boot-partition ${DREAMBOX_PART0_SIZE}:${DEPLOY_DIR_IMAGE}/secondstage-${MACHINE}.bin \
        --data-partition ${DREAMBOX_PART1_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
        --data-partition ${DREAMBOX_PART2_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
        > ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi; \
"

IMAGE_CMD_sum.jffs2.nfi = " \
    mkfs.jffs2 \
        --root=${IMAGE_ROOTFS}/boot \
        --compression-mode=none \
        --output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
        ${EXTRA_IMAGECMD}; \
    sumtool \
        -i ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
        -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.sum.jffs2 \
        ${EXTRA_IMAGECMD}; \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    mkfs.jffs2 \
        --root=${IMAGE_ROOTFS} \
        --enable-compressor=lzo \
        --compression-mode=priority \
        --output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
        ${EXTRA_IMAGECMD}; \
    sumtool \
        -i ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 \
        -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.sum.jffs2 \
        ${EXTRA_IMAGECMD}; \
    ${DREAMBOX_BUILDIMAGE} \
        --boot-partition ${DREAMBOX_PART0_SIZE}:${DEPLOY_DIR_IMAGE}/secondstage-${MACHINE}.bin \
        --data-partition ${DREAMBOX_PART1_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.sum.jffs2 \
        --data-partition ${DREAMBOX_PART2_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.sum.jffs2 \
        > ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi; \
"

IMAGE_CMD_ubi.nfi = " \
    mkfs.jffs2 \
        --root=${IMAGE_ROOTFS}/boot \
        --compression-mode=none \
        --output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
        ${EXTRA_IMAGECMD}; \
    if 	[ "${MACHINE}" = "dm7020hd" ]; then \
        mkfs.jffs2 \
            --root=${IMAGE_ROOTFS}/boot \
            --compression-mode=none \
            --output=${DEPLOY_DIR_IMAGE}/${IMAGE_NAMEV2}.boot.jffs2 \
            ${EXTRA_IMAGECMDV2}; \
    fi; \		
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    echo \[root\] > ubinize.cfg; \
    echo mode=ubi >> ubinize.cfg; \
    echo image=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs >> ubinize.cfg; \
    echo vol_id=0 >> ubinize.cfg; \
    echo vol_name=${UBI_VOLNAME} >> ubinize.cfg; \
    echo vol_type=dynamic >> ubinize.cfg; \
    if [ ${UBINIZE_VOLSIZE} = "0" ]; then \
        echo vol_flags=autoresize >> ubinize.cfg; \
    else \
        echo vol_size=${UBINIZE_VOLSIZE} >> ubinize.cfg; \
        if [ ${UBINIZE_DATAVOLSIZE} != "0" ]; then \
            echo \[data\] >> ubinize.cfg; \
            echo mode=ubi >> ubinize.cfg; \
            echo vol_id=1 >> ubinize.cfg; \
            echo vol_type=dynamic >> ubinize.cfg; \
            echo vol_name=data >> ubinize.cfg; \
            echo vol_size=${UBINIZE_DATAVOLSIZE} >> ubinize.cfg; \
            echo vol_flags=autoresize >> ubinize.cfg; \
            printf '/dev/ubi0_1\t/data\t\tubifs\trw\t\t\t\t0 0\n' >> ${IMAGE_ROOTFS}/etc/fstab; \
            install -d ${IMAGE_ROOTFS}/data; \
        fi; \
    fi; \
    mkfs.ubifs \
        -r ${IMAGE_ROOTFS} \
        -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs \
        ${MKUBIFS_ARGS}; \
    ubinize -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${UBINIZE_ARGS} ubinize.cfg; \
    ${DREAMBOX_BUILDIMAGE} \
        --boot-partition ${DREAMBOX_PART0_SIZE}:${DEPLOY_DIR_IMAGE}/secondstage-${MACHINE}.bin \
        --data-partition ${DREAMBOX_PART1_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.boot.jffs2 \
        --data-partition ${DREAMBOX_PART2_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi \
        > ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi; \
    if 	[ "${MACHINE}" = "dm7020hd" ]; then \
        echo dm7020hdv2 > ${IMAGE_ROOTFS}/etc/.machine; \
        echo \[root\] > ubinize.cfg; \
        echo mode=ubi >> ubinize.cfg; \
        echo image=${DEPLOY_DIR_IMAGE}/${IMAGE_NAMEV2}.rootfs.ubifs >> ubinize.cfg; \
        echo vol_id=0 >> ubinize.cfg; \
        echo vol_name=${UBI_VOLNAME} >> ubinize.cfg; \
        echo vol_type=dynamic >> ubinize.cfg; \
        if [ ${UBINIZE_VOLSIZEV2} = "0" ]; then \
            echo vol_flags=autoresize >> ubinize.cfg; \
        else \
            echo vol_size=${UBINIZE_VOLSIZEV2} >> ubinize.cfg; \
             if [ ${UBINIZE_DATAVOLSIZEV2} != "0" ]; then \
                echo \[data\] >> ubinize.cfg; \
                echo mode=ubi >> ubinize.cfg; \
                echo vol_id=1 >> ubinize.cfg; \
                echo vol_type=dynamic >> ubinize.cfg; \
                echo vol_name=data >> ubinize.cfg; \
                echo vol_size=${UBINIZE_DATAVOLSIZEV2} >> ubinize.cfg; \
                echo vol_flags=autoresize >> ubinize.cfg; \
                printf '/dev/ubi0_1\t/data\t\tubifs\trw\t\t\t\t0 0\n' >> ${IMAGE_ROOTFS}/etc/fstab; \
                install -d ${IMAGE_ROOTFS}/data; \
            fi; \
        fi; \
        mkfs.ubifs \
            -r ${IMAGE_ROOTFS} \
            -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAMEV2}.rootfs.ubifs \
            ${MKUBIFS_ARGSV2}; \
        ubinize -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAMEV2}.rootfs.ubi ${UBINIZE_ARGSV2} ubinize.cfg; \
        ${DREAMBOX_BUILDIMAGEV2} \
            --boot-partition ${DREAMBOX_PART0_SIZE}:${DEPLOY_DIR_IMAGE}/secondstage-${MACHINE}.bin \
            --data-partition ${DREAMBOX_PART1_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAMEV2}.boot.jffs2 \
            --data-partition ${DREAMBOX_PART2_SIZE}:${DEPLOY_DIR_IMAGE}/${IMAGE_NAMEV2}.rootfs.ubi \
            > ${DEPLOY_DIR_IMAGE}/${IMAGE_NAMEV2}.nfi; \
    fi; \
"

EXTRA_IMAGECMD_jffs2.nfi ?= "-e ${DREAMBOX_ERASE_BLOCK_SIZE} -n -l"
EXTRA_IMAGECMD_sum.jffs2.nfi ?= "-e ${DREAMBOX_ERASE_BLOCK_SIZE} -n -l"
EXTRA_IMAGECMD_ubi.nfi ?= "-e ${DREAMBOX_ERASE_BLOCK_SIZE} -n -l"
EXTRA_IMAGECMDV2_ubi.nfi ?= "-e ${DREAMBOX_ERASE_BLOCK_SIZEV2} -n -l"

IMAGE_DEPENDS_jffs2.nfi = "${IMAGE_DEPENDS_jffs2} dreambox-buildimage-native"
IMAGE_DEPENDS_sum.jffs2.nfi = "${IMAGE_DEPENDS_sum.jffs2} dreambox-buildimage-native"
IMAGE_DEPENDS_ubi.nfi = "${IMAGE_DEPENDS_ubi} ${IMAGE_DEPENDS_ubifs} dreambox-buildimage-native"

IMAGE_TYPES += "jffs2.nfi sum.jffs2.nfi ubi.nfi"
