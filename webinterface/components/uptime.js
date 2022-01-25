import React from "react";
import { api_request } from "../js/api";

export default class Uptime extends React.Component {
	constructor(props) {
		super(props);
		this.state = { uptime: "Please wait..." };

		api_request("/api/v2/uptime").then(data => {
			data = JSON.parse(data);

			this.setState({ uptime: data["uptime-dhms"] });
		});
	}

	render() {
		return (
			<div>
				<h1>Uptime</h1>
				<p>{ this.state.uptime }</p>
			</div>
		)
	}
}