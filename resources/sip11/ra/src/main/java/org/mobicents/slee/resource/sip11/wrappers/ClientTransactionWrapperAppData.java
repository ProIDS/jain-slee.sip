/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.mobicents.slee.resource.sip11.wrappers;

import gov.nist.javax.sip.stack.SIPClientTransaction;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.sip.ClientTransaction;

import org.mobicents.slee.resource.sip11.SipResourceAdaptor;

public class ClientTransactionWrapperAppData extends TransactionWrapperAppData<ClientTransaction, ClientTransactionWrapper> {

    private static final long serialVersionUID = 8707077860142474847L;
    private String associatedServerTransactionId;    
    
    public ClientTransactionWrapperAppData() {
        super();
    }
    
    public ClientTransactionWrapperAppData(ClientTransactionWrapper transactionWrapper) {
        super(transactionWrapper);
    }

    @Override
    public ClientTransactionWrapper getTransactionWrapper(ClientTransaction t, SipResourceAdaptor ra) {
        if (transactionWrapper == null) {
            transactionWrapper = new ClientTransactionWrapper((SIPClientTransaction) t, ra);
            transactionWrapper.setAssociatedServerTransaction(associatedServerTransactionId, false);
        }
        return transactionWrapper;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        if (transactionWrapper != null) {
            associatedServerTransactionId = transactionWrapper.getAssociatedServerTransactionId();
        }
        if (associatedServerTransactionId != null) {
            out.writeBoolean(true);
            out.writeUTF(associatedServerTransactionId);
        } else {
            out.writeBoolean(false);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        if (in.readBoolean()) {
            associatedServerTransactionId = in.readUTF();
        }
    }

}
