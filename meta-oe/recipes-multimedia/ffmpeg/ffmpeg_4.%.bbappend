FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS += "libxml2"

PACKAGECONFIG_append = " libass libbluray libfreetype librtmp libvorbis \
                        mp3lame openjpeg openssl vpx x265 libv4l2"

PACKAGECONFIG[libass] = "--enable-libass,--disable-libass,libass"
PACKAGECONFIG[libbluray] = "--enable-libbluray --enable-protocol=bluray,--disable-libbluray,libbluray"
PACKAGECONFIG[libfreetype] = "--enable-libfreetype,--disable-libfreetype,freetype"
PACKAGECONFIG[librtmp] = "--enable-librtmp,--disable-librtmp,librtmp rtmpdump"
PACKAGECONFIG[openjpeg] = "--enable-libopenjpeg,--disable-libopenjpeg,openjpeg"
PACKAGECONFIG[libv4l2] = "--enable-libv4l2,--disable-libv4l2,v4l-utils"
PACKAGECONFIG[gnutls] = "--enable-gnutls,--disable-gnutls"

MIPSFPU = "${@bb.utils.contains('TARGET_FPU', 'soft', '--disable-mipsfpu', '--enable-mipsfpu', d)}"

SRC_URI_append += " \
	file://4_02_fix_mpegts.patch \
	file://4_10_rtsp_patch \
	file://4_11_dxva2_patch \
	"

EXTRA_FFCONF = " \
	--prefix=${prefix} \
	--disable-runtime-cpudetect \
	--disable-ffplay \
	--enable-ffprobe \
	\
	--disable-doc \
	--disable-htmlpages \
	--disable-manpages \
	--disable-podpages \
	--disable-txtpages \
	\
	--disable-altivec \
	--disable-amd3dnow \
	--disable-amd3dnowext \
	--disable-mmx \
	--disable-mmxext \
	--disable-sse \
	--disable-sse2 \
	--disable-sse3 \
	--disable-ssse3 \
	--disable-sse4 \
	--disable-sse42 \
	--disable-avx \
	--disable-xop \
	--disable-fma3 \
	--disable-fma4 \
	--disable-avx2 \
	--disable-inline-asm \
	--disable-yasm \
	--disable-x86asm \
	--disable-fast-unaligned \
	\
	--disable-dxva2 \
	--disable-vaapi \
	--disable-vdpau \
	\
	--disable-muxers \
	--enable-muxer=apng \
	--enable-muxer=flac \
	--enable-muxer=mp3 \
	--enable-muxer=h261 \
	--enable-muxer=h263 \
	--enable-muxer=h264 \
	--enable-muxer=h265 \
	--enable-muxer=hevc \
	--enable-muxer=image2 \
	--enable-muxer=image2pipe \
	--enable-muxer=m4v \
	--enable-muxer=matroska \
	--enable-muxer=mjpeg \
	--enable-muxer=mp4 \
	--enable-muxer=mpeg1video \
	--enable-muxer=mpeg2video \
	--enable-muxer=mpegts \
	--enable-muxer=ogg \
	\
	--disable-parsers \
	--enable-parser=aac \
	--enable-parser=aac_latm \
	--enable-parser=ac3 \
	--enable-parser=dca \
	--enable-parser=dvbsub \
	--enable-parser=dvd_nav \
	--enable-parser=dvdsub \
	--enable-parser=flac \
	--enable-parser=h264 \
	--enable-parser=h265 \
	--enable-parser=hevc \
	--enable-parser=mjpeg \
	--enable-parser=mpeg4video \
	--enable-parser=mpegvideo \
	--enable-parser=mpegaudio \
	--enable-parser=png \
	--enable-parser=vc1 \
	--enable-parser=vorbis \
	--enable-parser=vp8 \
	--enable-parser=vp9 \
	\
	--disable-encoders \
	--enable-encoder=aac \
	--enable-encoder=h261 \
	--enable-encoder=h263 \
	--enable-encoder=h263p \
	--enable-encoder=jpeg2000 \
	--enable-encoder=jpegls \
	--enable-encoder=ljpeg \
	--enable-encoder=mjpeg \
	--enable-encoder=mpeg1video \
	--enable-encoder=mpeg2video \
	--enable-encoder=mpeg4 \
	--enable-encoder=png \
	--enable-encoder=rawvideo \
	\
	--disable-decoders \
	--enable-decoder=aac \
	--enable-decoder=aac_latm \
	--enable-decoder=adpcm_ct \
	--enable-decoder=adpcm_g722 \
	--enable-decoder=adpcm_g726 \
	--enable-decoder=adpcm_g726le \
	--enable-decoder=adpcm_ima_amv \
	--enable-decoder=adpcm_ima_oki \
	--enable-decoder=adpcm_ima_qt \
	--enable-decoder=adpcm_ima_rad \
	--enable-decoder=adpcm_ima_wav \
	--enable-decoder=adpcm_ms \
	--enable-decoder=adpcm_sbpro_2 \
	--enable-decoder=adpcm_sbpro_3 \
	--enable-decoder=adpcm_sbpro_4 \
	--enable-decoder=adpcm_swf \
	--enable-decoder=adpcm_yamaha \
	--enable-decoder=alac \
	--enable-decoder=ape \
	--enable-decoder=atrac1 \
	--enable-decoder=atrac3 \
	--enable-decoder=atrac3p \
	--enable-decoder=ass \
	--enable-decoder=cook \
	--enable-decoder=dca \
	--enable-decoder=dsd_lsbf \
	--enable-decoder=dsd_lsbf_planar \
	--enable-decoder=dsd_msbf \
	--enable-decoder=dsd_msbf_planar \
	--enable-decoder=dvbsub \
	--enable-decoder=dvdsub \
	--enable-decoder=eac3 \
	--enable-decoder=evrc \
	--enable-decoder=flac \
	--enable-decoder=g723_1 \
	--enable-decoder=g729 \
	--enable-decoder=h261 \
	--enable-decoder=h263 \
	--enable-decoder=h263i \
	--enable-decoder=h264 \
	--enable-decoder=h264_v4l2m2m \
	--enable-decoder=h265 \
	--enable-decoder=hevc \
	--enable-decoder=hevc_v4l2m2m \
	--enable-decoder=iac \
	--enable-decoder=imc \
	--enable-decoder=jpeg2000 \
	--enable-decoder=jpegls \
	--enable-decoder=mace3 \
	--enable-decoder=mace6 \
	--enable-decoder=metasound \
	--enable-decoder=mjpeg \
	--enable-decoder=mlp \
	--enable-decoder=movtext \
	--enable-decoder=mp1 \
	--enable-decoder=mp2 \
	--enable-decoder=mp3 \
	--enable-decoder=mp3adu \
	--enable-decoder=mp3on4 \
	--enable-decoder=mpeg1video \
	--enable-decoder=mpeg2video \
	--enable-decoder=mpeg4 \
	--enable-decoder=nellymoser \
	--enable-decoder=opus \
	--enable-decoder=pcm_alaw \
	--enable-decoder=pcm_bluray \
	--enable-decoder=pcm_dvd \
	--enable-decoder=pcm_f32be \
	--enable-decoder=pcm_f32le \
	--enable-decoder=pcm_f64be \
	--enable-decoder=pcm_f64le \
	--enable-decoder=pcm_lxf \
	--enable-decoder=pcm_mulaw \
	--enable-decoder=pcm_s16be \
	--enable-decoder=pcm_s16be_planar \
	--enable-decoder=pcm_s16le \
	--enable-decoder=pcm_s16le_planar \
	--enable-decoder=pcm_s24be \
	--enable-decoder=pcm_s24daud \
	--enable-decoder=pcm_s24le \
	--enable-decoder=pcm_s24le_planar \
	--enable-decoder=pcm_s32be \
	--enable-decoder=pcm_s32le \
	--enable-decoder=pcm_s32le_planar \
	--enable-decoder=pcm_s8 \
	--enable-decoder=pcm_s8_planar \
	--enable-decoder=pcm_u16be \
	--enable-decoder=pcm_u16le \
	--enable-decoder=pcm_u24be \
	--enable-decoder=pcm_u24le \
	--enable-decoder=pcm_u32be \
	--enable-decoder=pcm_u32le \
	--enable-decoder=pcm_u8 \
	--enable-decoder=pcm_zork \
	--enable-decoder=pgssub \
	--enable-decoder=png \
	--enable-decoder=qcelp \
	--enable-decoder=qdm2 \
	--enable-decoder=ra_144 \
	--enable-decoder=ra_288 \
	--enable-decoder=ralf \
	--enable-decoder=s302m \
	--enable-decoder=sipr \
	--enable-decoder=shorten \
	--enable-decoder=sonic \
	--enable-decoder=srt \
	--enable-decoder=ssa \
	--enable-decoder=subrip \
	--enable-decoder=subviewer \
	--enable-decoder=subviewer1 \
	--enable-decoder=tak \
	--enable-decoder=text \
	--enable-decoder=truehd \
	--enable-decoder=truespeech \
	--enable-decoder=tta \
	--enable-decoder=vorbis \
	--enable-decoder=vp9_v4l2m2m \
	--enable-decoder=wmalossless \
	--enable-decoder=wmapro \
	--enable-decoder=wmav1 \
	--enable-decoder=wmav2 \
	--enable-decoder=wmavoice \
	--enable-decoder=wavpack \
	--enable-decoder=xsub \
	\
	--disable-demuxers \
	--enable-demuxer=aac \
	--enable-demuxer=ac3 \
	--enable-demuxer=apng \
	--enable-demuxer=ass \
	--enable-demuxer=avi \
	--enable-demuxer=dts \
	--enable-demuxer=dash \
	--enable-demuxer=ffmetadata \
	--enable-demuxer=flac \
	--enable-demuxer=flv \
	--enable-demuxer=h264 \
	--enable-demuxer=h265 \
	--enable-demuxer=hls \
	--enable-demuxer=image2 \
	--enable-demuxer=image2pipe \
	--enable-demuxer=image_bmp_pipe \
	--enable-demuxer=image_jpeg_pipe \
	--enable-demuxer=image_jpegls_pipe \
	--enable-demuxer=image_png_pipe \
	--enable-demuxer=m4v \
	--enable-demuxer=matroska \
	--enable-demuxer=mjpeg \
	--enable-demuxer=mov \
	--enable-demuxer=mp3 \
	--enable-demuxer=mpegts \
	--enable-demuxer=mpegtsraw \
	--enable-demuxer=mpegps \
	--enable-demuxer=mpegvideo \
	--enable-demuxer=mpjpeg \
	--enable-demuxer=ogg \
	--enable-demuxer=pcm_s16be \
	--enable-demuxer=pcm_s16le \
	--enable-demuxer=realtext \
	--enable-demuxer=rawvideo \
	--enable-demuxer=rm \
	--enable-demuxer=rtp \
	--enable-demuxer=rtsp \
	--enable-demuxer=srt \
	--enable-demuxer=vc1 \
	--enable-demuxer=wav \
	--enable-demuxer=webm_dash_manifest \
	\
	--disable-filters \
	--enable-filter=scale \
	--enable-filter=drawtext \
	--enable-filter=overlay \
	\
	--enable-zlib \
	--enable-bzlib \
	--enable-openssl \
	--enable-libass \
	--enable-bsfs \
	--disable-xlib \
	--disable-libxcb \
	--disable-libxcb-shm \
	--disable-libxcb-xfixes \
	--disable-libxcb-shape \
	\
	--enable-shared \
	--enable-network \
	--enable-nonfree \
	--enable-small \
	--enable-stripping \
	--disable-static \
	--disable-debug \
	--disable-runtime-cpudetect \
	--enable-pic \
	--enable-pthreads \
	--enable-hardcoded-tables \
	\
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "${MIPSFPU} --disable-mmi --disable-vfp --disable-neon --disable-mipsdsp --disable-mipsdspr2", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "--enable-armv6 --enable-armv6t2 --enable-vfp --enable-neon", "", d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "aarch64", "--enable-armv8 --enable-vfp --enable-neon", "", d)} \
    --extra-cflags="${TARGET_CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS} -ffunction-sections -fdata-sections -fno-aggressive-loop-optimizations" \
    --extra-ldflags="${TARGET_LDFLAGS},--gc-sections -Wl,--print-gc-sections,-lrt" \
"
