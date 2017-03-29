const helpers = require('../helpers.js');

import React from 'react';

/*export const ItemCartComponent = props => <table style={{width: '500px'}}>
    <thead><tr><th style={{width: '260px'}}>Name</th><th style={{width: '100px'}}>Price</th><th>Quantity</th><th>&nbsp;</th></tr></thead>
    <tbody>
    { props.cart.map((nextProduct, index) => <tr key={index}>
        <td className="storeProductName">{nextProduct.name}</td><td className="storePriceCell">${nextProduct.cost}</td><td style={{textAlign: 'center'}}>{nextProduct.quantity}</td><td className="buttonCell"><button onClick={() => props.removeFunc(nextProduct)}>Remove</button></td>
    </tr>)}
    </tbody>
</table>;*/

export class ItemCartComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            removeFunc: props.removeFunc
        };
    }

    componentDidUpdate() {
    }

    render() {
        return <table style={{width: '500px'}}>
            <thead><tr><th style={{width: '260px'}}>Name</th><th style={{width: '100px'}}>Price</th><th>Quantity</th><th>&nbsp;</th></tr></thead>
            <tbody>
            { this.props.cart.map((nextProduct, index) => <tr key={index}>
                <td className="storeProductName">{nextProduct.name}</td><td className="storePriceCell">${nextProduct.cost}</td><td style={{textAlign: 'center'}}>{nextProduct.quantity}</td><td className="buttonCell"><button onClick={() => this.state.removeFunc(nextProduct)}>Remove</button></td>
            </tr>)}
            </tbody>
        </table>;
    }
}
