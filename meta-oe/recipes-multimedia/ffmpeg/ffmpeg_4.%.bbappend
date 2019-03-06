PR .= ".1"

RSUGGESTS_${PN} = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PROVIDES =+ " libavcodec53 libavformat53 libav"
PACKAGES =+ " libavcodec53 libavformat53 libav"

DEPENDS += "libxml2 librtmp"

PACKAGECONFIG[librtmp] = "--enable-librtmp,--disable-librtmp,rtmpdump"
PACKAGECONFIG[libbluray] = "--enable-libbluray --enable-protocol=bluray,--disable-libbluray,libbluray"
PACKAGECONFIG[libfreetype] = "--enable-libfreetype,--disable-libfreetype,freetype"

PACKAGECONFIG = "avdevice avfilter avcodec avformat avresample swscale swresample postproc \
		bzlib gpl x264 openssl libbluray libfreetype librtmp mp3lame theora libvorbis lzma vpx"

MIPSFPU = "${@bb.utils.contains('TARGET_FPU', 'soft', '--disable-mipsfpu', '--enable-mipsfpu', d)}"

SRC_URI_append += " \
	file://02_fix_mpegts.patch \
	file://03_allow_to_choose_rtmp_impl_at_runtime.patch \
	file://04_hls_replace_key_uri.patch \
	file://06_optimize_aac.patch \
	file://07_increase_buffer_size.patch \
	file://08_recheck_discard_flags.patch \
	file://09_ffmpeg_fix_edit_list_parsing.patch \
	"

EXTRA_FFCONF = " \
    --disable-static \
    --disable-runtime-cpudetect \
    --enable-ffprobe \
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
    --disable-armv5te \
    --disable-armv6 \
    --disable-armv6t2 \
    --disable-vfp \
    --disable-neon \
    --disable-inline-asm \
    --disable-yasm \
    --disable-fast-unaligned \
    --disable-muxers \
    --enable-muxer=mpeg1video \
    --enable-muxer=h264 \
    --enable-muxer=mp4 \
    --enable-muxer=image2 \
    --enable-muxer=mjpeg \
    --enable-muxer=rawvideo \
    --enable-muxer=mpeg2video \
    --enable-muxer=matroska \
    --enable-muxer=m4v \
    --enable-muxer=image2pipe \
    --enable-muxer=apng \
    --enable-muxer=mpegts \
    --enable-encoders \
    --enable-encoder=mpeg1video \
    --enable-encoder=png \
    --enable-encoder=libx264 \
    --enable-encoder=ljpeg \
    --enable-encoder=mpeg4 \
    --enable-encoder=jpeg2000 \
    --enable-encoder=jpegls \
    --enable-encoder=rawvideo \
    --disable-decoders \
    --enable-decoder=aac \
    --enable-decoder=aac_latm \
    --enable-decoder=ac3 \
    --enable-decoder=adpcm_ima_ws \
    --enable-decoder=adpcm_4xm \
    --enable-decoder=adpcm_adx \
    --enable-decoder=adpcm_ct \
    --enable-decoder=adpcm_ea \
    --enable-decoder=adpcm_ea_maxis_xa \
    --enable-decoder=adpcm_ea_r1 \
    --enable-decoder=adpcm_ea_r2 \
    --enable-decoder=adpcm_ea_r3 \
    --enable-decoder=adpcm_ea_xas \
    --enable-decoder=adpcm_g726 \
    --enable-decoder=adpcm_ima_amv \
    --enable-decoder=adpcm_ima_dk3 \
    --enable-decoder=adpcm_ima_dk4 \
    --enable-decoder=adpcm_ima_ea_eacs \
    --enable-decoder=adpcm_ima_ea_sead \
    --enable-decoder=adpcm_ima_iss \
    --enable-decoder=adpcm_ima_qt \
    --enable-decoder=adpcm_ima_smjpeg \
    --enable-decoder=adpcm_ima_wav \
    --enable-decoder=adpcm_ima_ws \
    --enable-decoder=adpcm_ms \
    --enable-decoder=adpcm_sbpro_2 \
    --enable-decoder=adpcm_sbpro_3 \
    --enable-decoder=adpcm_sbpro_4 \
    --enable-decoder=adpcm_swf \
    --enable-decoder=adpcm_thp \
    --enable-decoder=adpcm_xa \
    --enable-decoder=adpcm_yamaha \
    --enable-decoder=alac \
    --enable-decoder=ape \
    --enable-decoder=amrnb \
    --enable-decoder=amrwb \
    --enable-decoder=atrac1 \
    --enable-decoder=atrac3 \
    --enable-decoder=cavs \
    --enable-decoder=cook \
    --enable-decoder=dca \
    --enable-decoder=eac3 \
    --enable-decoder=flac \
    --enable-decoder=h263 \
    --enable-decoder=h264 \
    --enable-decoder=hevc \
    --enable-decoder=huffyuv \
    --enable-decoder=indeo3 \
    --enable-decoder=indeo4 \
    --enable-decoder=indeo5 \
    --enable-decoder=jpeg2000 \
    --enable-decoder=jpegls \
    --enable-decoder=mace3 \
    --enable-decoder=mace6 \
    --enable-decoder=metasound \
    --enable-decoder=mjpeg \
    --enable-decoder=mlp \
    --enable-decoder=mp1 \
    --enable-decoder=mp2 \
    --enable-decoder=mp3 \
    --enable-decoder=mpegvideo \
    --enable-decoder=mpeg1video \
    --enable-decoder=mpeg2video \
    --enable-decoder=mpeg4 \
    --enable-decoder=mpc7 \
    --enable-decoder=mpc8 \
    --enable-decoder=opus \
    --enable-decoder=qcelp \
    --enable-decoder=qdm2 \
    --enable-decoder=pcm_alaw \
    --enable-decoder=pcm_bluray \
    --enable-decoder=pcm_dvd \
    --enable-decoder=pcm_f32be \
    --enable-decoder=pcm_f32le \
    --enable-decoder=pcm_f64be \
    --enable-decoder=pcm_f64le \
    --enable-decoder=pcm_mulaw \
    --enable-decoder=pcm_s16be \
    --enable-decoder=pcm_s16le \
    --enable-decoder=pcm_s16le_planar \
    --enable-decoder=pcm_s24be \
    --enable-decoder=pcm_s24daud \
    --enable-decoder=pcm_s24le \
    --enable-decoder=pcm_s32be \
    --enable-decoder=pcm_s32le \
    --enable-decoder=pcm_s8 \
    --enable-decoder=pcm_u16be \
    --enable-decoder=pcm_u16le \
    --enable-decoder=pcm_u24be \
    --enable-decoder=pcm_u24le \
    --enable-decoder=pcm_u32be \
    --enable-decoder=pcm_u32le \
    --enable-decoder=pcm_u8 \
    --enable-decoder=pcm_zork \
    --enable-decoder=pgssub \
    --enable-decoder=rv10 \
    --enable-decoder=rv20 \
    --enable-decoder=rv30 \
    --enable-decoder=rv40 \
    --enable-decoder=sipr \
    --enable-decoder=svq1 \
    --enable-decoder=svq3 \
    --enable-decoder=targa \
    --enable-decoder=theora \
    --enable-decoder=truehd \
    --enable-decoder=vc1 \
    --enable-decoder=vorbis \
    --enable-decoder=vp3 \
    --enable-decoder=vp5 \
    --enable-decoder=vp6 \
    --enable-decoder=vp6a \
    --enable-decoder=vp6f \
    --enable-decoder=vp7 \
    --enable-decoder=vp8 \
    --enable-decoder=vp9 \
    --enable-decoder=wavpack \
    --enable-decoder=wmalossless \
    --enable-decoder=wmapro \
    --enable-decoder=wmav1 \
    --enable-decoder=wmav2 \
    --enable-decoder=wmavoice \
    --enable-decoder=wmv1 \
    --enable-decoder=wmv2 \
    --enable-decoder=wmv3 \
    --enable-decoder=bmp \
    --enable-decoder=gif \
    --enable-decoder=png \
    --enable-decoder=tiff \
    --disable-demuxer=adp \
    --disable-demuxer=adx \
    --disable-demuxer=afc \
    --disable-demuxer=anm \
    --disable-demuxer=apc \
    --disable-demuxer=ast \
    --disable-demuxer=avs \
    --disable-demuxer=bethsoftvid \
    --disable-demuxer=bfi \
    --disable-demuxer=bink \
    --disable-demuxer=bmv \
    --disable-demuxer=brstm \
    --disable-demuxer=c93 \
    --disable-demuxer=cdg \
    --disable-demuxer=dnxhd \
    --disable-demuxer=dsicin \
    --disable-demuxer=dfa \
    --disable-demuxer=dxa \
    --disable-demuxer=ea \
    --disable-demuxer=ea_cdata \
    --disable-demuxer=frm \
    --disable-demuxer=gsm \
    --disable-demuxer=gxf \
    --disable-demuxer=hnm \
    --disable-demuxer=ico \
    --disable-demuxer=ilbc \
    --disable-demuxer=iss \
    --disable-demuxer=jv \
    --disable-demuxer=mm \
    --disable-demuxer=paf \
    --disable-demuxer=pva \
    --disable-demuxer=qcp \
    --disable-demuxer=redspark \
    --disable-demuxer=rl2 \
    --disable-demuxer=roq \
    --disable-demuxer=rsd \
    --disable-demuxer=rso \
    --disable-demuxer=siff \
    --disable-demuxer=smush \
    --disable-demuxer=sol \
    --disable-demuxer=thp \
    --disable-demuxer=tiertexseq \
    --disable-demuxer=tmv \
    --disable-demuxer=tty \
    --disable-demuxer=txd \
    --disable-demuxer=vqf \
    --disable-demuxer=wsaud \
    --disable-demuxer=wsvqa \
    --disable-demuxer=xa \
    --disable-demuxer=xbin \
    --disable-demuxer=yop \
    --disable-demuxer=ingenient \
    --disable-demuxer=image_dds_pipe \
    --disable-demuxer=image_dpx_pipe \
    --disable-demuxer=image_exr_pipe \
    --disable-demuxer=image_j2k_pipe \
    --disable-demuxer=image_pictor_pipe \
    --disable-demuxer=image_qdraw_pipe \
    --disable-demuxer=image_sgi_pipe \
    --disable-demuxer=image_sunrast_pipe \
    --enable-demuxer=image2 \
    --enable-demuxer=image2pipe \
    --enable-demuxer=m4v \
    --enable-demuxer=mpegts \
    --enable-demuxer=apng \
    --enable-demuxer=image_jpeg_pipe \
    --enable-demuxer=image_jpegls_pipe \
    --enable-demuxer=image_png_pipe \
    --enable-demuxer=realtext \
    --enable-demuxer=rawvideo \
    --enable-demuxer=ffmetadata \
    --enable-demuxer=image_bmp_pipe \
    --enable-demuxer=matroska \
    --enable-demuxer=mp4 \
    --enable-demuxer=h264 \
    --enable-demuxer=mpegvideo \
    --enable-parser=h264 \
    --enable-parser=mjpeg \
    --enable-parser=mpeg4video \
    --enable-parser=mpegvideo \
    --enable-parser=png \
    --disable-filters \
    --enable-filter=scale \
    --enable-filter=drawtext \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "${MIPSFPU} --disable-vfp --disable-neon --disable-mipsdsp --disable-mipsdspr2", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "--enable-armv6 --enable-armv6t2 --enable-vfp --enable-neon", "", d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "aarch64", "--enable-armv8 --enable-vfp --enable-neon", "", d)} \
    --disable-debug \
    --pkg-config="pkg-config" \
    --enable-zlib \
    --extra-cflags="${TARGET_CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS} -ffunction-sections -fdata-sections -fno-aggressive-loop-optimizations" \
    --extra-ldflags="${TARGET_LDFLAGS},--gc-sections -Wl,--print-gc-sections,-lrt" \
    --prefix=${prefix} \
"
