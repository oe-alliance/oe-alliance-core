# Generate the C21 U-Boot resources from the distribution's splash artwork.
from argparse import ArgumentParser
from array import array
from gzip import compress, decompress
from os.path import join
from struct import iter_unpack, pack_into, unpack_from
from sys import byteorder
from zlib import crc32
from PIL.Image import Resampling, Transpose, open as openImage


def splashBitmap(template, artwork):
	pixels = unpack_from("<I", template, 10)[0]
	width, height, planes, depth, compression = unpack_from("<iiHHI", template, 18)
	if template[:2] != b"BM" or (width, height, planes, depth, compression) != (1920, 1080, 1, 16, 3) or unpack_from("<III", template, 54) != (0xf800, 0x07e0, 0x001f):
		raise ValueError("Unexpected GTX splash bitmap format")
	with openImage(artwork) as image:
		frame = image.convert("RGB").resize((width, height), Resampling.LANCZOS).transpose(Transpose.FLIP_TOP_BOTTOM)
	# U-Boot expects bottom-up little-endian RGB565 scanlines.
	colors = array("H", ((red >> 3) << 11 | (green >> 2) << 5 | (blue >> 3) for red, green, blue in iter_unpack("3B", frame.tobytes())))
	if byteorder != "little":
		colors.byteswap()
	return template[:pixels] + colors.tobytes() + template[pixels + width * height * 2:]


def buildLogo(stock, artwork):
	if len(stock) != 8 * 1024 * 1024 or stock[8:16] != b"AML_RES!":
		raise ValueError("Expected the GTX 8 MiB logo partition")
	checksum, version = unpack_from("<II", stock)
	length, count, alignment = unpack_from("<III", stock, 16)
	if version != 2 or count != 9 or alignment != 16 or not 640 <= length <= len(stock):
		raise ValueError("Unexpected GTX logo resource header")
	if checksum != (crc32(stock[4:length]) ^ 0xffffffff):
		raise ValueError("Logo resource CRC mismatch")
	headerSize = (count + 1) * 64
	output = bytearray(stock[:headerSize])
	targets = set()
	for index in range(count):
		header = 64 * (index + 1)
		name = stock[header + 32:header + 64].split(b"\0")[0]
		size, offset = unpack_from("<II", stock, header + 8)
		if offset < headerSize or offset % alignment or offset + size > length:
			raise ValueError("Invalid logo resource bounds")
		item = stock[offset:offset + size]
		if name in (b"bootup", b"bootup_X3"):
			if not targets:
				bitmap = splashBitmap(decompress(item), artwork)
				bootup = compress(bitmap, compresslevel=9, mtime=0)
			item = bootup
			targets.add(name)
		pack_into("<II", output, header + 8, len(item), len(output))
		output.extend(item)
		output.extend(bytes(-len(output) % alignment))
	if targets != {b"bootup", b"bootup_X3"} or len(output) > len(stock):
		raise ValueError("Boot resources missing or output too large")
	pack_into("<I", output, 16, len(output))
	pack_into("<I", output, 0, crc32(output[4:]) ^ 0xffffffff)
	return output.ljust(len(stock), b"\0"), bitmap


if __name__ == "__main__":
	parser = ArgumentParser(description="Build the GTX splash for FAT and the eMMC logo partition.")
	parser.add_argument("template")
	parser.add_argument("artwork")
	parser.add_argument("outputDirectory")
	args = parser.parse_args()
	with open(args.template, "rb") as source:
		resource, bitmap = buildLogo(source.read(), args.artwork)
	with open(join(args.outputDirectory, "logo.img"), "wb") as target:
		target.write(resource)
	with open(join(args.outputDirectory, "splash.bmp"), "wb") as target:
		target.write(bitmap)
