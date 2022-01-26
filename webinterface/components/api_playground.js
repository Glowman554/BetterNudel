import React from "react";
import { api_request, get_api_path, process_url } from "../js/api";
import { get_color } from "../js/style";
import Card from "./card";

export default class ApiPlayground extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			endpoints: [],
			current_url: "",
			current_url_output: "nothing yet...",
		}

		api_request("/api/v2/endpoints").then(data => {
			data = JSON.parse(data);

			this.state.endpoints = data;
			this.setState(this.state);
		});
	}

	render() {
		return (
			<div>
				<Card>
					<h2>Api url input</h2>
					<div key={this.state.current_url}>
						<input defaultValue={this.state.current_url} style={{
							width: "100%"
						}} type={"url"} contentEditable onKeyDown={
							event => {
								switch (event.key) {
									case "Enter":
										{
											this.state.current_url = event.target.value;

											console.log("Requesting " + this.state.current_url);
											this.state.current_url_output = "loading...";
											this.setState(this.state);

											fetch(this.state.current_url).then(response => response.text()).then(data => {
												this.state.current_url_output = data;
												this.setState(this.state);
											});
										}
										break;
									
				
								}
								this.setState(this.state);
							}
						}></input>
					</div>

					<br />
					<br />
					<div>
						<h2>Output</h2>
						<p style={{
							backgroundColor: "black",
							whiteSpace: "pre-wrap",
							color: "#00ff00"
						}}>
							{String(this.state.current_url_output)}
						</p>
					</div>
				</Card>
				<Card>
					<h2>Endpoints</h2>
					<ul className={"bar card left-align " + get_color().base_color} style={{
							margin: "0px",
							padding: "0px"
					}}>
						{ this.state.endpoints.map(endpoint => {
							return (
								<li style={{
									listStyle: "none",
									margin: "0px",
								}} className={"padding-large " +  get_color().color + " hover-" + get_color().base_color} onClick={
									event => {
										this.state.current_url = process_url(endpoint);
										this.setState(this.state);
									}
								}>
									{endpoint}
								</li>
							)
						}) }
					</ul>
				</Card>
			</div>
		)
	}
}