# openDroid image hooks
MAINTAINER = "OpenDroid Team"

image_preprocess() {
    # Set root password to blank
    sed 's%^root:[^:]*:%root::%' < ${IMAGE_ROOTFS}/etc/shadow > ${IMAGE_ROOTFS}/etc/shadow.new
    mv ${IMAGE_ROOTFS}/etc/shadow.new ${IMAGE_ROOTFS}/etc/shadow
}
IMAGE_PREPROCESS_COMMAND += "image_preprocess; "
