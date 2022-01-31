import Head from 'next/head'
import React from 'react'
import Button from '../components/button'
import Card from '../components/card'
import Footer from '../components/footer'
import Heading from '../components/heading'
import Login from '../components/login'
import Navbar from '../components/navbar'
import NoSSR from '../components/no_ssr'
import Uploads from '../components/uploads'
import { api_request } from '../js/api'
import { root_style } from '../js/style'

export default class Admin_ extends React.Component {
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
			<NoSSR>
				<div className="container" style={root_style}>
					<Head>
						<title>Admin</title>
						<link rel="icon" href="/favicon.ico" />
						<link rel="stylesheet" href="/style.css" />
					</Head>

					<Navbar title="Admin" />

					<Heading title="BetterNudel" subtitle="Admin site!"/>

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

					<Footer />
				</div>
			</NoSSR>
		)
	}
}
