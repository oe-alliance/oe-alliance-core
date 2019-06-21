# Define multilib target
require conf/multilib.conf
MULTILIBS = "multilib:lib32"
DEFAULTTUNE_virtclass-multilib-lib32 = "armv7ahf-neon"
 
# Specify the 32-bit libraries to be added to the image
IMAGE_INSTALL_append = " lib32-glibc lib32-libgcc lib32-libstdc++ lib32-libcrypto0.9.8 lib32-libssl0.9.8 lib32-libcrypto lib32-libssl lib32-libcrypto-compat-0.9.7 lib32-libcrypto-compat-1.0.0 lib32-libxcrypt lib32-libxcrypt-compat"

OEM_ARCH = "armv7ahf-neon"