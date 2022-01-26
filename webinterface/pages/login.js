import Head from 'next/head'
import Card from '../components/card'
import Footer from '../components/footer'
import Heading from '../components/heading'
import Login from '../components/login'
import Navbar from '../components/navbar'
import NoSSR from '../components/no_ssr'
import { root_style } from '../js/style'

export default function Login_() {
	return (
		<NoSSR>
			<div className="container" style={root_style}>
				<Head>
					<title>Login</title>
					<link rel="icon" href="/favicon.ico" />
					<link rel="stylesheet" href="/style.css" />
				</Head>

				<Navbar />

				<Heading title="BetterNudel" subtitle="Login site!"/>

				<Card>
					<Login />
				</Card>

				<Footer />
			</div>
		</NoSSR>
	)
}
