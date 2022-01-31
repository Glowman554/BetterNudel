import React from "react";
import { api_request } from "../js/api";
import Button from "./button";
import Card from "./card";
import Uploads from "./uploads";

export default class Admin extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			suggestions: []
		}

		this.test_permission("full_access").then(() => {
			this.test_permission("suggestions").then(() => {
				this.request_suggestions();
			});
		});
	}

	request_suggestions() {
		api_request("/api/v2/suggestions").then(data => {
			data = JSON.parse(data);
			this.setState({
				suggestions: data
			});
		});
	}

	test_permission(perm) {
		return new Promise((resolve, reject) => {
			api_request("/api/v2/has_permission?permission=" + perm).then(data => {
				data = JSON.parse(data);
				if (!data) {
					alert("Permission " + perm + " not found");
					location.href = "/";
					reject();
				} else {
					console.log("Permission " + perm + " found");
					resolve();
				}
			});
		});
	}

	render() {
		return (
			<div>
				<Card>
					<h1>Suggestions</h1>
					<br />
					<ul>
						{this.state.suggestions.map(suggestion => {
							return (
								<li>
									<p>{suggestion}</p>
								</li>
							);
						})}
					</ul>
					<br />
					<Button onClick={
						event => {
							api_request("/api/v2/suggestions/delete").then(data => {
								alert(data);
								this.request_suggestions();
							})
						}
					}>Clear suggestions</Button>
				</Card>

				<Card>
					<h1>Uploads</h1>
					<br />
					<Uploads all_uploads={true} />
				</Card>
			</div>
		)
	}
}
