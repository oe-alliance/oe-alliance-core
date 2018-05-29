# Define multilib target
require conf/multilib.conf
MULTILIBS = "multilib:lib32"
DEFAULTTUNE_virtclass-multilib-lib32 = "armv7ahf-neon"
 
# Specify the 32-bit libraries to be added to the image
IMAGE_INSTALL_append = " lib32-glibc lib32-libgcc lib32-libstdc++"