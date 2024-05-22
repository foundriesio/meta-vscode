DESCRIPTION = "Packaging for prebuilt versions of vscode"
HOMEPAGE = "https://code.visualstudio.com/download"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../VSCode-linux-arm64/LICENSES.chromium.html;md5=c590e5227ea914c9a4187d5b1cbefe93"

COMPATIBLE_HOST = "(x86_64|aarch64|arm).*-linux"

inherit bin_package

def get_host_arch(d):
    arch = d.getVar('HOST_ARCH')
    vsarch = ""
    if arch == "aarch64":
        vsarch = "arm64"
    elif arch == "arm":
        vsarch = "armhf"
    elif arch == "x86-64":
        vsarch = "x64"
    else:
        # Not supported
        exit(1)
    return vsarch

VSCODE_ARCH ?= "${@get_host_arch(d)}"

GIT_SHA = "dc96b837cf6bb4af9cd736aa3af08cf8279f7685"
TIMESTAMP-arm64 = "1715058820"
TIMESTAMP-armhf = "1715058860"
TIMESTAMP-x64 = "1715058993"
SRC_URI = "https://vscode.download.prss.microsoft.com/dbazure/download/stable/${GIT_SHA}/code-stable-${VSCODE_ARCH}-${TIMESTAMP-${VSCODE_ARCH}}.tar.gz;name=vscode-${VSCODE_ARCH}"
SRC_URI[vscode-arm64.md5sum] = "c71bd32ab70f5615621e61c548be9e4c"
SRC_URI[vscode-armhf.md5sum] = "68e224b57fa70999002819aa5869ed91"
SRC_URI[vscode-x64.md5sum] = "d36230c467c3a4f929881f3a61756971"

do_install() {
    install -d ${D}/usr/share/vscode
    cp -r ${WORKDIR}/VSCode-linux-arm64/* ${D}/usr/share/vscode
}

FILES_${PN} = "/usr/share/vscode/*"
INSANE_SKIP:${PN} += "already-stripped file-rdeps ldflags libdir"
