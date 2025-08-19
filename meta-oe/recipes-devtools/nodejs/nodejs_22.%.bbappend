# ARMv7/NEON fix
CFLAGS:append:arm = " -flax-vector-conversions"
CXXFLAGS:append:arm = " -flax-vector-conversions"
