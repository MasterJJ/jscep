/*
 * Copyright (c) 2009 David Grant
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.google.code.jscep.response;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Capabilities {
    public static enum Capability {
        GET_NEXT_CA_CERT(),
        POST_PKI_OPERATION(),
        RENEWAL(),
        SHA_512(),
        SHA_256(),
        SHA_1(),
        TRIPLE_DES();
    }
    
    private Set<Capability> capabilties = new HashSet<Capability>();
    private Map<String, Capability> map = new HashMap<String, Capability>();
    {
    	map.put("GetNextCACert", Capability.GET_NEXT_CA_CERT);
    	map.put("POSTPKIOperation", Capability.POST_PKI_OPERATION);
    	map.put("Renewal", Capability.RENEWAL);
    	map.put("SHA-512", Capability.SHA_512);
    	map.put("SHA-256", Capability.SHA_256);
    	map.put("SHA-1", Capability.SHA_1);
    	map.put("DES3", Capability.TRIPLE_DES);
    }
    
    public Capabilities(List<String> capabilities) {
    	for (String capability : capabilities) {
    		this.capabilties.add(map.get(capability));
    	}
    }

    private boolean supports(Capability capability) {
        return capabilties.contains(capability);
    }

    /**
     * Returns <tt>true</tt> if the CA supports CA key rollover, <tt>false</tt> otherwise.
     * 
     * @return <tt>true</tt> if the CA supports CA key rollover, <tt>false</tt> otherwise.
     */
    public boolean supportsCaKeyRollover() {
        return supports(Capability.GET_NEXT_CA_CERT);
    }

    /**
     * Returns <tt>true</tt> if the CA supports HTTP POST requests, <tt>false</tt> otherwise.
     * 
     * @return <tt>true</tt> if the CA supports HTTP POST requests, <tt>false</tt> otherwise.
     */
    public boolean supportsPost() {
        return supports(Capability.POST_PKI_OPERATION);
    }

    /**
     * Returns <tt>true</tt> if the CA supports certificate renewal, <tt>false</tt> otherwise.
     * 
     * @return <tt>true</tt> if the CA supports certificate renewal, <tt>false</tt> otherwise.
     */
    public boolean supportsRenewal() {
        return supports(Capability.RENEWAL);
    }

    /**
     * Returns <tt>true</tt> if the CA supports SHA-1, <tt>false</tt> otherwise.
     * 
     * @return <tt>true</tt> if the CA supports SHA-1, <tt>false</tt> otherwise.
     */
    public boolean supportsSHA1() {
        return supports(Capability.SHA_1);
    }

    /**
     * Returns <tt>true</tt> if the CA supports SHA-256, <tt>false</tt> otherwise.
     * 
     * @return <tt>true</tt> if the CA supports SHA-256, <tt>false</tt> otherwise.
     */
    public boolean supportsSHA256() {
        return supports(Capability.SHA_256);
    }

    /**
     * Returns <tt>true</tt> if the CA supports SHA-512, <tt>false</tt> otherwise.
     * 
     * @return <tt>true</tt> if the CA supports SHA-512, <tt>false</tt> otherwise.
     */
    public boolean supportsSHA512() {
        return supports(Capability.SHA_512);
    }

    /**
     * Returns <tt>true</tt> if the CA supports TripleDES, <tt>false</tt> otherwise.
     * 
     * @return <tt>true</tt> if the CA supports TripleDES, <tt>false</tt> otherwise.
     */
    public boolean supportsTripleDES() {
        return supports(Capability.TRIPLE_DES);
    }
    
    /**
     * DESede > DES
     * 
     * @return
     * @link http://java.sun.com/javase/6/docs/technotes/guides/security/StandardNames.html#Cipher
     */
    public String getPreferredCipher() {
    	if (supportsTripleDES()) {
    		return "DESede";
    	} else {
    		return "DES";
    	}
    }
    
    /**
     * SHA-512 > SHA-256 > SHA-1 > MD5
     * 
     * @return
     * @link http://java.sun.com/javase/6/docs/technotes/guides/security/StandardNames.html#MessageDigest
     */
    public String getPreferredMessageDigest() {
    	if (supportsSHA512()) {
    		return "SHA-512";
    	} else if (supportsSHA256()) {
    		return "SHA-256";
    	} else if (supportsSHA1()) {
    		return "SHA-1";
    	} else {
    		return "MD5";
    	}
    }

    @Override
    public String toString() {
        return capabilties.toString();
    }
}