/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2012, Pyravlos Team
 *
 * http://www.strabon.di.uoa.gr/
 */

package gr.uoa.di.madgik.sesame.functions;

import org.openrdf.query.algebra.BinaryValueOperator;
import org.openrdf.query.algebra.QueryModelVisitor;
import org.openrdf.query.algebra.ValueExpr;

/**
 * @author Konstantina Bereta <Konstantina.Bereta@di.uoa.gr>
 *
 */
public class EHCoversFunc extends BinaryValueOperator{
	/**
	 * Checks if two RDF spatial literals spatially overlap.
	 */

		/*--------------*
		 * Constructors *
		 *--------------*/

		public void EHCoversFunc() {
		}
		
		public EHCoversFunc(ValueExpr leftArg, ValueExpr rightArg) {
			super(leftArg, rightArg);
		}

	

		/*---------*
		 * Methods *
		 *---------*/

	

		@Override
		public boolean equals(Object other) {
			return other instanceof EHCoversFunc && super.equals(other);
		}

		@Override
		public int hashCode() {
			return super.hashCode() ^ "EHCoversFunc".hashCode();
		}

		@Override
		public EHCoversFunc clone() {
			return (EHCoversFunc)super.clone();
		}

		@Override
		public <X extends Exception> void visit(QueryModelVisitor<X> visitor)
				throws X {
			visitor.meet(this);
						
		}

}


		
	
